package com.carvia.models.vto;

import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.VehicleVo;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class VehicleAdVto {
    private final VehicleVo vehicle;
    private final AnuncioVo anuncio;

    public VehicleAdVto(VehicleVo vehicle, AnuncioVo anuncio) {
        this.vehicle = vehicle;
        this.anuncio = anuncio;
    }

    public VehicleAdVto(String resultMarca, String modelo, int ano, String resultProvincia, String descripcion,
            double resultPrecio) {
                this.vehicle = new VehicleVo(resultMarca, modelo, ano);
                this.anuncio = new AnuncioVo(resultProvincia, descripcion, resultPrecio);
    }

    // Métodos para acceder a VehicleVo
    public String getMarca() { return vehicle.getMarca(); }
    public String getModelo() { return vehicle.getModelo(); }
    public int getAnio() { return vehicle.getAnio(); }

    // Métodos para acceder a AnuncioVo
    public double getPrecio() { return anuncio.getPrecio(); }
    public String getDescripcion() { return anuncio.getDescripcion(); }
    //public String getImagen() { return anuncio.getImagen(); }

}
