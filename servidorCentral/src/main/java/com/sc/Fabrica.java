package com.sc;

import com.sc.controladores.ActividadController;
import com.sc.controladores.DepartamentoController;
import com.sc.controladores.InscripcionController;
import com.sc.controladores.PaqueteController;
import com.sc.controladores.SalidaController;
import com.sc.controladores.UsuarioController;
import com.sc.interfaces.IActividadController;
import com.sc.interfaces.IDepartamentoController;
import com.sc.interfaces.IInscripcionController;
import com.sc.interfaces.IPaqueteController;
import com.sc.interfaces.ISalidaController;
import com.sc.interfaces.IUsuarioController;

public class Fabrica {
    private static Fabrica instancia;
    
    private Fabrica(){};

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    // Entrega las interfaces de los controladores.
    public IUsuarioController getIUsuarioController() {
        return new UsuarioController();
    }

    public IPaqueteController getIPaqueteController() {
        return new PaqueteController();
    }

    public IDepartamentoController getIDepartamentoController() {
        return new DepartamentoController();
    }

    public IActividadController getIActividadController() {
        return new ActividadController();
    }
    
    public ISalidaController getISalidaController() {
        return new SalidaController();
    }
   
    public IInscripcionController getIInscripcionController() {
    	return new InscripcionController();
    }
}
