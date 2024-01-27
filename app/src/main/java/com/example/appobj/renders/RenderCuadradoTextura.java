package com.example.appobj.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.appobj.R;
import com.example.appobj.geometrias.CuadradoTextura;
import com.example.appobj.geometrias.MarTextura;


public class RenderCuadradoTextura implements GLSurfaceView.Renderer {
    private float vIncremento;
    private CuadradoTextura atardecer;
    private MarTextura mar;
    private float rotacion = 0;

    //Se utilizará para almacenar las identificaciones de textura generadas por OpenGL.
    private int[] arrayTexturas = new int[2];
    private Context context;

    public RenderCuadradoTextura(Context context){
        this.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.234f,0.247f,0.255f,1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glEnable(gl.GL_TEXTURE_2D);

        atardecer = new CuadradoTextura();

        gl.glGenTextures(2, arrayTexturas, 0);

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.atardecer);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap.recycle();

        Bitmap bitmap1;
        mar = new MarTextura();

        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mar);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[1]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap1, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap1.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        float near = 1.0f;
        float far = 30.0f;
        gl.glViewport(0, 0, ancho, alto);//origen "x=0" y "y=0" por defecto alto y ancho de la pantalla, es practicamente la ventana de copordenas donde se va a dibujar
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();//
        gl.glFrustumf(left, right, bottom, top, near, far);

        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);//Configura el modo de mezcla de textura. Aqui usado para que no se mezcle con los colores definidos en la geometria.
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        vIncremento += 0.22f;

        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl,
                -5.0f, 5.0f, 5.0f,   // Posición de la cámara
                0.0f, 0.0f, 0.0f,   // Punto de mira
                0, 1, 0);            // Orientación de la cámara (eje Y arriba)

        // Aplicar rotación a la escena alrededor del eje Y
        //gl.glRotatef(rotacion, 0.0f, 1.0f, 0.0f);

        gl.glPushMatrix(); {

        gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);// Textura a usar
        gl.glTranslatef(0, 2, -3);
        gl.glScalef(3.0f, 2.0f, 1.0f);
        atardecer.dibujar(gl);

        }gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]);// Textura a usar
            gl.glTranslatef(0, 0, 0);
            gl.glScalef(3f, 0.2f, 3.0f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            mar.dibujar(gl);
        }gl.glPopMatrix();


        // Actualizar el valor de rotación para el próximo fotograma
        rotacion += 1.0f;
    }

}