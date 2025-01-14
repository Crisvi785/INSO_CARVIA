package com.carvia.models.vto;

import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.VehicleVo;

public class VehicleAdVto {
    private final VehicleVo vehicle;
    private final AnuncioVo anuncio;

    public VehicleAdVto(VehicleVo vehicle, AnuncioVo anuncio) {
        this.vehicle = vehicle;
        this.anuncio = anuncio;
    }

    public VehicleAdVto(String resultMarca, String modelo, int ano, String resultProvincia, String descripcion,
            double resultPrecio, int idAnuncio, int idVehiculo, int idUsuario, String imagesUrl, int kilometraje, String tipoCombustible, String transmision) {
                this.vehicle = new VehicleVo(resultMarca, modelo, ano);
                this.vehicle.setId(idVehiculo);
                this.vehicle.setKilometraje(kilometraje);
                this.vehicle.setTipoCombustible(tipoCombustible);
                this.vehicle.setTransmision(transmision);
                this.anuncio = new AnuncioVo(resultProvincia, descripcion, resultPrecio);
                this.anuncio.setId(idAnuncio);
                this.anuncio.setIdVehiculo(idVehiculo);
                this.anuncio.setIdUsuario(idUsuario);
                this.anuncio.setUrl(imagesUrl);
    }

    // Métodos para acceder a VehicleVo
    public String getMarca() { return vehicle.getMarca(); }
    public String getModelo() { return vehicle.getModelo(); }
    public int getAno() { return vehicle.getAnio(); }
    public int getKilometraje() { return vehicle.getKilometraje(); }
    public String getCombustible() { return vehicle.getTipoCombustible(); }
    public String getTransmision() { return vehicle.getTransmision(); }
    public int getId() { return vehicle.getId(); }

    // Métodos para acceder a AnuncioVo
    public double getPrecio() { return anuncio.getPrecio(); }
    public String getDescripcion() { return anuncio.getDescripcion(); }
    public String getProvincia() { return anuncio.getProvincia(); }
    public int getIdUsuario() { return anuncio.getIdUsuario(); }
    public String getImagen() { return anuncio.getUrlFoto(); }

}
