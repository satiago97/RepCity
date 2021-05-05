package com.example.repcity.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


@FormUrlEncoded
@POST("api/login")
fun userLogin(
    @Field("email") email: String,
    @Field("password") password: String
):Call<User>


    @GET("api/incidentes")
    fun getIncidentes(): Call<List<Address>>

    @GET("api/incidentes/acidente")
    fun getIncidenteAcidente(): Call<List<Address>>

    @GET("api/incidentes/incendio")
    fun getIncidenteIncendio(): Call<List<Address>>

    @GET("api/incidentes/outros")
    fun getIncidenteOutros(): Call<List<Address>>

    @GET("api/incidentes/obras")
    fun getIncidenteObras(): Call<List<Address>>

    @FormUrlEncoded
    @POST("api/inserirIncidente")
    fun addPoint(@Field("tipo") tipo: String,
                 @Field("descricao") descricao: String,
                 @Field("id_user") id_user: Int,
                 @Field("lat") lat: Double,
                 @Field("lng") lng: Double
    ): Call<Address>


    @DELETE("api/eliminarIncidente/{id}")
    fun deletePoint(@Path("id") id: Int): Call<Address>

}