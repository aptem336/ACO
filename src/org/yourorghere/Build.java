package org.yourorghere;

import javax.media.opengl.GL;
import static org.yourorghere.ACO.gl;
import static org.yourorghere.ACO.glut;

public class Build {

    public static void buildSphere(double x, double y, double z, double size, int color) {
        gl.glPushMatrix();
        gl.glTranslated(x, y, z);
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        glut.glutSolidSphere(size, 30, 30);
        gl.glPopMatrix();
    }

    public static void buildLightSphere(double x, double y, double z, double size, int color) {
        gl.glPushMatrix();
        gl.glTranslated(x, y, z);
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        glut.glutSolidSphere(size, 5, 5);
        gl.glPopMatrix();
    }

    public static void buildCube(double x, double y, double z, double size, int color) {
        gl.glPushMatrix();
        gl.glTranslated(x, y, z);
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        glut.glutSolidCube((float) size);
        gl.glPopMatrix();
    }

    public static void buildTriangle(double[][] vertex, int color) {
        gl.glPushMatrix();
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        gl.glBegin(GL.GL_POLYGON);
        for (double[] currentVertex : vertex) {
            gl.glVertex2d(currentVertex[0], currentVertex[1]);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    public static void buildCircuit(double[][] vertex, int color) {
        GL gl = ACO.gl;
        gl.glPushMatrix();
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (double[] vertex1 : vertex) {
            gl.glVertex2d(vertex1[0], vertex1[1]);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    public static void buildLine(double[][] vertex, int color) {
        GL gl = ACO.gl;
        gl.glPushMatrix();
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        gl.glLineWidth(2);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(vertex[0][0], vertex[0][1]);
        gl.glVertex2d(vertex[1][0], vertex[1][1]);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public static void buildPoint(double[] vertex, int color) {
        GL gl = ACO.gl;
        gl.glPushMatrix();
        gl.glColor4f(((color >> 24) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color) & 0xFF) / 255f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(vertex[0], vertex[1]);
        gl.glEnd();
        gl.glPopMatrix();
    }
}
