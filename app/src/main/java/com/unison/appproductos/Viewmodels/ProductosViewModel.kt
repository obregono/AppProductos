package com.unison.appproductos.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unison.appproductos.models.Producto
import com.unison.appproductos.room.ProductDatabaseDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel(
    val productDatabaseDao: ProductDatabaseDao
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    // Nuevo estado para manejar el éxito al agregar un producto
    private val _exitoAlAgregar = MutableStateFlow(false)
    val exitoAlAgregar: StateFlow<Boolean> = _exitoAlAgregar.asStateFlow()

    init {
        recuperarProductos()
    }

    private fun recuperarProductos() {
        viewModelScope.launch {
            // Recoge los productos desde la base de datos
            productDatabaseDao.getAllProducts().collect { productosList ->
                _productos.value = productosList
            }
        }
    }

    fun refrescarProductos() {
        recuperarProductos()
    }

    fun agregarProducto(nombre: String, descripcion: String, precio: Double, fechaRegistro: String): Boolean {
        if (nombre.isEmpty() || descripcion.isEmpty() || precio <= 0 || fechaRegistro.isEmpty()) {
            return false
        }
        val nuevoProducto = Producto(
            id = (_productos.value.size + 1).toString(),
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            fechaRegistro = fechaRegistro
        )
        viewModelScope.launch {
            productDatabaseDao.insertProduct(nuevoProducto)
            _exitoAlAgregar.value = true
            recuperarProductos()
        }
        return true
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            productDatabaseDao.deleteProduct(producto)
            recuperarProductos()
        }
    }

    fun actualizarProducto(id: String, nombre: String, descripcion: String, precio: Double, fechaRegistro: String) {
        viewModelScope.launch {
            val productoActualizado = Producto(id, nombre, descripcion, fechaRegistro, precio)
            productDatabaseDao.updateProduct(productoActualizado)
            recuperarProductos()
        }
    }

    fun resetearEstadoExito() {
        _exitoAlAgregar.value = false
    }
}
