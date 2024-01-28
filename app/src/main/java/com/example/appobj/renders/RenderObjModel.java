package com.example.appobj.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.appobj.R;
import com.example.appobj.geometrias.Cilindro;
import com.example.appobj.geometrias.CuadradoTextura;
import com.example.appobj.geometrias.MarTextura;
import com.example.appobj.geometrias.ObjModel;
import com.example.appobj.geometrias.PiramideTextura;
import com.example.appobj.geometrias.Rectangulo;
import com.example.appobj.utilidades.MisColores;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderObjModel implements GLSurfaceView.Renderer {
    private float vIncremento;
    private ObjModel mona, dona, langosta, casco;
    private CuadradoTextura atardecer;
    private MarTextura mar, arena,toalla;
    private float rotacion = 0;
    private Cilindro sombrilla;
    private PiramideTextura triangulo;
    private int[] arrayTexturas = new int[8];
    private Context context;
    double[][] coloresCilindros = {
            {0.5, 0.5, 0.5, 1.0}, //sombrilla
    };

    public RenderObjModel(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.07059f, 0.07059f, 0.07059f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        mona = new ObjModel("monaBlender.obj", MisColores.random(967), this.context);
        dona = new ObjModel("donaBlender.obj", MisColores.blancoYnegro(1000), this.context);
        langosta = new ObjModel("langosta.obj", new float[]{1, 0f, 0f, 1}, this.context);//10 000 poligonos/vertices
        casco = new ObjModel("casco.obj", MisColores.random(2500), this.context);

        // Inicializar objeto Cilindro
        float cilindroRadius = 1.0f;
        float cilindroHeight = 2.0f;
        int cilindroNumSlices = 30;

        sombrilla = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);

        gl.glEnable(gl.GL_TEXTURE_2D);

        atardecer = new CuadradoTextura();

        gl.glGenTextures(8, arrayTexturas, 0);

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

        Bitmap bitmap2;
        arena = new MarTextura();

        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.arena);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[2]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap2, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap2.recycle();

        Bitmap bitmap3;
        triangulo = new PiramideTextura();

        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cara1);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[3]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap3, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cara2);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[4]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap3, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cara3);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[5]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap3, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.cara4);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[6]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap3, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap3.recycle();

        Bitmap bitmap4;
        toalla = new MarTextura();

        bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.toalla);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[7]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap4, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap4.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        float near = 1.0f;
        float far = 50.0f;

        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();//
        gl.glFrustumf(left, right, bottom, top, near, far);

        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);//Configura el modo de mezcla de textura. Aqui usado para que no se mezcle con los colores definidos en la geometria.

        GLU.gluLookAt(gl,
                -5, 5, 5,
                0, 0, 0,
                0, 1, 0
        );
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        vIncremento += 0.5f;
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        //TODA LA ESCENA--------------------------------------------------
        gl.glRotatef(vIncremento * 2, 0f, 1, 0);
        //----------------------------------------------------------------

        /*gl.glPushMatrix();
        {
            gl.glTranslatef(0, 5, 0);
            //gl.glScalef(1.2f,1.2f,1.2f);
            gl.glRotatef(vIncremento * 2, 1, 1, 1);
            dona.dibujar(gl);
        }
        gl.glPopMatrix();*/

        /*gl.glPushMatrix();
        {
            gl.glTranslatef(0, 2f, 0);
            gl.glScalef(1.2f, 1.2f, 1.2f);
            mona.dibujar(gl);
        }
        gl.glPopMatrix();*/

        /*gl.glPushMatrix();
        {
            gl.glTranslatef(0, -0.5f, 0);
            gl.glScalef(1.5f, 1.5f, 1.5f);
            casco.dibujar(gl);
        }
        gl.glPopMatrix();*/

        gl.glPushMatrix();
        {
            gl.glTranslatef(4, -1f, 4);
            gl.glScalef(0.15f, 0.15f, 0.15f);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 0, 1);
            gl.glRotatef(180, 0, 1, 0);
            langosta.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);// Textura a usar
            gl.glTranslatef(0, 2.5f, -3);
            gl.glScalef(5.0f, 3.5f, 1.0f);
            atardecer.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]);// Textura a usar
            gl.glTranslatef(0, -1, 0);
            gl.glScalef(5f, 0.2f, 3.0f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            mar.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[2]);// Textura a usar
            gl.glTranslatef(0, -1, 4);
            gl.glScalef(5f, 0.2f, 1.0f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            arena.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-3, 0, 4);
            gl.glScalef(0.1f, 0.9f, 0.1f);
            sombrilla.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
            gl.glEnableClientState(gl.GL_TEXTURE_COORD_ARRAY);//Estado de las coordenadas de textura

            //AFECTA TODA LA ESCENA--------------------------
            gl.glTranslatef(-3, 0.9f, 4);
            gl.glScalef(0.6f, 0.25f, 0.6f);
            //gl.glRotatef(-vIncremento * 2, 1f, 0f, 0f);
            //gl.glRotatef(-vIncremento, 0f, 1f, 0f);
            //-----------------------------------------------

            //gl.glRotatef(180, 1,0,0);

            // Triangulo0
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[3]);//Textura a usar
            triangulo.dibujarCara(gl, 0);

            // Triangulo1
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[4]);
            triangulo.dibujarCara(gl, 1);

            // Triangulo2
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[5]);
            triangulo.dibujarCara(gl, 2);


            // Triangulo3
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[6]);
            triangulo.dibujarCara(gl, 3);

            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[7]);// Textura a usar
            gl.glTranslatef(0, -0.8f, 4);
            gl.glScalef(2f, 0.2f, 0.4f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            toalla.dibujar(gl);
        }gl.glPopMatrix();

    }
}
