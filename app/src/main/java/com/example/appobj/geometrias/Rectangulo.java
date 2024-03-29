package com.example.appobj.geometrias;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Rectangulo {
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;

    private final static int byteFlotante =4;
    private final static int compPorVertice=3;

    private float[] color;
    public Rectangulo() {
        float[] vertices = {
                -1.0f,1.0f,1.0f, //0
                3.0f,1.0f,1.0f, //1
                3.0f,-1.0f,1.0f, //2
                -1.0f,-1.0f,1.0f, //3

                -1.0f,1.0f,-1.0f, //4
                3.0f,1.0f,-1.0f, //5
                3.0f,-1.0f,-1.0f, //6
                -1.0f,-1.0f,-1.0f //7

        };
        byte[] indices = {
                0,1,2,
                0,2,3,
                0,4,5,
                0,5,1,
                0,4,7,
                0,7,3,

                6,2,3,
                6,3,7,
                6,1,5,
                6,2,1,
                6,4,5,
                6,7,4
        };



        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length*byteFlotante);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);

        bufferIndices= ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);


    }

    public void dibujar (GL10 gl){
        gl.glFrontFace(gl.GL_CW);
        bufferVertices.position(0);
        gl.glVertexPointer(compPorVertice, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);


        // frontal:
        bufferIndices.position(0);
        gl.glColor4f(0.3725f, 0.7412f, 0.8314f, 1);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

// superior:
        bufferIndices.position(6);
        gl.glColor4f(0.2f, 1.0f, 0.1f, 1);  //piso
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

// izquierda:
        bufferIndices.position(12);
        gl.glColor4f(0.3725f, 0.7412f, 0.8314f, 1);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

// derecha:
        bufferIndices.position(18);
        gl.glColor4f(0.3725f, 0.7412f, 0.8314f, 1);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

// abajo:
        bufferIndices.position(24);
        gl.glColor4f(0.3725f, 0.7412f, 0.8314f, 1);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

// atras:
        bufferIndices.position(30);
        gl.glColor4f(0.3725f, 0.7412f, 0.8314f, 1);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glDisableClientState(gl.GL_COLOR_ARRAY);
    }

}