class Actividad {
    constructor(nombreA, descripcion, duracion, costeUni, ciudad, fechaCrea, proveedor, departamento) {
    this.nombreA = nombreA;
    this.descripcion = descripcion;
    this.duracion = duracion;
    this.costeUni = costeUni;
    this.ciudad = ciudad;
    this.fechaCrea = fechaCrea;
      this.proveedor = proveedor;  // Representación del proveedor (asume que es un objeto)
      this.departamento = departamento;  // Representación del departamento (asume que es un objeto)
      this.paquetes = [];  // Lista de paquetes (asume que es una matriz de objetos)
      this.salidas = [];  // Lista de salidas (asume que es una matriz de objetos)
    }
}