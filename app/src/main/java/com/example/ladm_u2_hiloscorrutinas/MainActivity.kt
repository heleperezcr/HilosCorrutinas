package com.example.ladm_u2_hiloscorrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    var numeros = arrayOf(5,2,10,4)
    var suma = 2
    var suma2 = 2
    var suma3 = 2

    var hilo = Hilo(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCorrutina.setOnClickListener {
            corrutina()
            runOnUiThread {
                corrutina.setText(suma.toString())
            }
        }

        btnHilo1.setOnClickListener {
            try {
                hilo.start()
            }catch (e:Exception){
                Toast.makeText(this,"Hilo ya ejecutado", Toast.LENGTH_LONG)
            }
        }

        btnCorrutina2.setOnClickListener {
            corrutina2()
        }

    }

    //-----------------------------------------------
    //Esta traba la interfaz
    //Se esta ejecutando en primer plano y es asincrono
    fun corrutina() = runBlocking {
        launch {
            //parte asincrona
            for(n in numeros){
                suma += n
                //Esto es para que vaya mas lento
                delay(1000)
            }
        }
        //Parte sincrona
        //Primero se ejecuta esta parte, despues la parte asincrona
        suma = suma *2
    }

//-----------------------------------------------
//Con esta corrutina si se ve el cambio en la interfaz por que se ejecuta en 2do plano
    fun corrutina2() = GlobalScope.launch {
        for(n in numeros){
            suma3 += n
            runOnUiThread {
                corrutina2.setText(suma3.toString())
            }//
            delay(2000)
        }
    }
//--------------------------------------------------

}




//---------aqui empieza el hilo--------
//Este no traba la interfaz
//este no es asincrono
class Hilo(p:MainActivity) : Thread(){
    var p=p

    override fun run() {
        super.run()
        for(n in p.numeros){
            p.suma2 += n
            //Esto es para que vaya mas lento
            sleep(1000)
        }
        p.suma2 = p.suma2 * 2
        p.hilo1.setText(p.suma2.toString())
    }
}


