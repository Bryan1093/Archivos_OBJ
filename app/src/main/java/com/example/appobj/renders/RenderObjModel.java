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
import com.example.appobj.geometrias.EsferaText;
import com.example.appobj.geometrias.MarTextura;
import com.example.appobj.geometrias.ObjModel;
import com.example.appobj.geometrias.PiramideTextura;
import com.example.appobj.geometrias.Rectangulo;
import com.example.appobj.utilidades.MisColores;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderObjModel implements GLSurfaceView.Renderer {
    private float vIncremento;
    private ObjModel dona, langosta, ave,canoa,palmera,palmera2,sombrero,langosta2, langosta3,ave2;
    private CuadradoTextura atardecer, atardecer2, atardecer3;
    private MarTextura mar, arena,toalla;
    private EsferaText balonPlaya;
    private Cilindro sombrilla;
    private PiramideTextura triangulo;
    private float translacion = 1;
    private float translacionDelta = 0.05f; // Incremento de translación por fotograma
    private static final float MAX_TRANSLATION = 1.8f; // Límite superior de translación
    private static final float MIN_TRANSLATION = -1.8f; // Límite inferior de translación
    private int[] arrayTexturas = new int[11];
    private Context context;
    double[][] coloresCilindros = {
            {0.5, 0.5, 0.5, 1.0}, //sombrilla
    };

    public RenderObjModel(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(1, 0.4667f, 0, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        //dona = new ObjModel("donaBlender.obj", MisColores.blancoYnegro(1000), this.context);
        dona = new ObjModel("donaBlender.obj", new float[]{0.902f, 0.0706f, 0.7922f, 1}, this.context);
        langosta = new ObjModel("langosta.obj", new float[]{1, 0f, 0f, 1}, this.context);//10 000 poligonos/vertices
        langosta2 = new ObjModel("langosta.obj", new float[]{0.5f, 0.25f, 0.0f, 1.0f}, this.context);//10 000 poligonos/vertices
        langosta3 = new ObjModel("langosta.obj", new float[]{0, 0, 1, 1}, this.context);//10 000 poligonos/vertices
        ave = new ObjModel("ave.obj", new float[]{0, 0, 0, 1}, this.context);//10 000 poligonos/vertices
        ave2 = new ObjModel("ave.obj", new float[]{0, 0, 0, 1}, this.context);//10 000 poligonos/vertices
        sombrero = new ObjModel("sombrero.obj", new float[]{0.4f, 0.0667f, 0.0667f, 1}, this.context);
        palmera = new ObjModel("palmera.obj", new float[]{0, 1, 0, 1}, this.context);
        palmera2 = new ObjModel("palmera.obj", new float[]{0, 1, 0, 1}, this.context);
        canoa = new ObjModel("cannoe.obj", new float[]{0.5451f, 0.2706f, 0.0745f, 1}, this.context);

        // Inicializar objeto Cilindro
        float cilindroRadius = 1.0f;
        float cilindroHeight = 2.0f;
        int cilindroNumSlices = 30;

        sombrilla = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);

        gl.glEnable(gl.GL_TEXTURE_2D);

        atardecer = new CuadradoTextura();

        gl.glGenTextures(11, arrayTexturas, 0);

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.atardecer);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap.recycle();

        Bitmap bitmap1;
        mar = new MarTextura();

        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.mar1);
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

        Bitmap bitmap5;
        balonPlaya = new EsferaText(30,30,1,1);

        bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pelota);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[8]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap5, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap5.recycle();

        Bitmap bitmap6;
        atardecer2 = new CuadradoTextura();

        bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.infinito);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[9]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap6, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap6.recycle();

        Bitmap bitmap7;
        atardecer3 = new CuadradoTextura();

        bitmap7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.infinito);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[10]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap7, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap7.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        float near = 1.0f;
        float far = 80.0f;

        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();//
        gl.glFrustumf(left, right, bottom, top, near, far);

        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);//Configura el modo de mezcla de textura. Aqui usado para que no se mezcle con los colores definidos en la geometria.

        GLU.gluLookAt(gl,
                0, 5, 20,
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

        gl.glPushMatrix();
        {

            if (translacion >= MAX_TRANSLATION || translacion <= MIN_TRANSLATION) {
                translacionDelta *= -1; // Cambiar la dirección
            }

            gl.glTranslatef(translacion, 0, 0);

            gl.glPushMatrix();
            {

                gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[8]);// Textura a usar
                gl.glTranslatef(1, -1f, 8.8f);
                gl.glScalef(0.6f, 0.6f, 0.6f);
                balonPlaya.dibujar(gl);

            }gl.glPopMatrix();


        gl.glPushMatrix();
        {

            gl.glTranslatef(3.5f, -1f, 9);
            gl.glScalef(0.2f, 0.2f, 0.2f);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(-90, 0, 0, 1);
            gl.glRotatef(180, 0, 1, 0);
            langosta.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-0.5f, -1f, 9);
            gl.glScalef(0.2f, 0.2f, 0.2f);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 0, 1);
            gl.glRotatef(180, 0, 1, 0);
            langosta2.dibujar(gl);
        }
        gl.glPopMatrix();

            translacion += translacionDelta;

        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-0.45f, -1f, 11);
            gl.glScalef(0.2f, 0.2f, 0.2f);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 0, 1);
            gl.glRotatef(180, 0, 1, 0);
            langosta3.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-4.7f, -0.5f, 10);
            gl.glScalef(0.04f, 0.04f, 0.04f);
            gl.glRotatef(60, 1, 1, 0);
            gl.glRotatef(270, 0, 0, 1);
            sombrero.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(0, -2f, 3);
            gl.glScalef(0.5f, 0.5f, 0.5f);
            canoa.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-6, -0.8f, 12);
            gl.glScalef(0.08f, 0.4f, 0.08f);
            palmera.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(6, -0.8f, 12);
            gl.glScalef(0.08f, 0.4f, 0.08f);
            palmera2.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(3, 5f, 3);
            gl.glRotatef(90,0,1,0);
            gl.glScalef(8f, 8f, 8f);
            ave.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-3, 4.5f, 3);
            gl.glRotatef(90,0,1,0);
            gl.glScalef(8f, 8f, 8f);
            ave2.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);// Textura a usar
            gl.glTranslatef(0, 4.5f, -3);
            gl.glScalef(7.0f, 5.5f, 1.0f);
            atardecer.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[9]);// Textura a usar
            gl.glTranslatef(7, 4.5f, 2);
            gl.glRotatef(90,0,1,0);
            gl.glScalef(5.0f, 5.5f, 1.0f);
            atardecer2.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[10]);// Textura a usar
            gl.glTranslatef(-7, 4.5f, 2);
            gl.glRotatef(90,0,1,0);
            gl.glScalef(5.0f, 5.5f, 1.0f);
            atardecer3.dibujar(gl);

        }gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]);// Textura a usar
            gl.glTranslatef(0, -1, 2f);
            gl.glScalef(7f, 0.2f, 5.0f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            mar.dibujar(gl);
        }gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[2]);// Textura a usar
            gl.glTranslatef(0, -1, 10f);
            gl.glScalef(7f, 0.2f, 3.0f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            arena.dibujar(gl);
        }gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(-5, 1, 10);
            gl.glScalef(0.1f, 2.5f, 0.1f);
            sombrilla.dibujar(gl);
        }
        gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
            gl.glEnableClientState(gl.GL_TEXTURE_COORD_ARRAY);//Estado de las coordenadas de textura

            //AFECTA TODA LA ESCENA--------------------------
            gl.glTranslatef(-5, 3.5f, 10);
            gl.glScalef(1f, 0.45f, 1f);
            //-----------------------------------------------

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
            gl.glTranslatef(0, -0.8f, 11);
            gl.glScalef(3f, 0.5f, 0.7f);
            gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
            toalla.dibujar(gl);

        }gl.glPopMatrix();


        gl.glPushMatrix();
        {
            gl.glTranslatef(1, 0f, 11);
            gl.glScalef(0.5f,0.5f,0.3f);
            dona.dibujar(gl);
        }
        gl.glPopMatrix();
    }
}
