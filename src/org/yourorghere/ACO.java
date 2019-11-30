package org.yourorghere;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class ACO implements GLEventListener {

    static GL gl;
    static GLU glu;
    static GLUT glut;
    static Interface mainFrame;
    static Setting setting;

    static long millis1;
    static long millis0;
    static int fps;
    static int framesCalc = 0;

    public static void main(String[] args) {
        mainFrame = new Interface();
        setting = new Setting();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();

//        gl.glEnable(GL.GL_LIGHTING);
//        gl.glEnable(GL.GL_LIGHT0);
//        gl.glLightiv(GL.GL_LIGHT0, GL.GL_POSITION, new int[]{0, 0, 1, 0}, 0);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glEnable(GL.GL_NORMALIZE);

        gl.glAlphaFunc(GL.GL_GREATER, 0);
        gl.glEnable(GL.GL_BLEND);
        gl.glEnable(GL.GL_ALPHA_TEST);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL.GL_DEPTH_TEST);

        gl.setSwapInterval(1);
        gl.glClearColor(((Data.mainColor >> 24) & 0xFF ^ 0xFF) / 255f, ((Data.mainColor >> 16) & 0xFF ^ 0xFF) / 255f, ((Data.mainColor >> 8) & 0xFF ^ 0xFF) / 255f, ((Data.mainColor) & 0xFF ^ 0xFF) / 255f);
        gl.glShadeModel(GL.GL_SMOOTH);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final double h = (double) width / (double) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 10000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, 0);

        glu.gluLookAt(0, 0, 300, 0, 0, 0, 0, 1, 0);
//<editor-fold defaultstate="collapsed" desc="Надписи">
        gl.glColor4f(((Data.solutionColor >> 24) & 0xFF) / 255f, ((Data.solutionColor >> 16) & 0xFF) / 255f, ((Data.solutionColor >> 8) & 0xFF) / 255f, ((Data.solutionColor) & 0xFF) / 255f);
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "RESULT: " + Math.round(Algorithm.getBestChainLength()));

        gl.glColor4f(((Data.mainColor >> 24) & 0xFF) / 255f, ((Data.mainColor >> 16) & 0xFF) / 255f, ((Data.mainColor >> 8) & 0xFF) / 255f, ((Data.mainColor) & 0xFF) / 255f);
        gl.glWindowPos2i(20, 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Right-click to open a setting and controls");

        gl.glColor4f(((Data.mainColor >> 24) & 0xFF) / 255f, ((Data.mainColor >> 16) & 0xFF) / 255f, ((Data.mainColor >> 8) & 0xFF) / 255f, ((Data.mainColor) & 0xFF) / 255f);
        /*
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 45);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.valueText1 + " " + Data.valueInWork1);
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 65);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.valueText2 + " " + Data.valueInWork2);
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 85);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.valueText3 + " " + Data.valueInWork3);
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 105);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.valueText4 + " " + Data.valueInWork4);
        gl.glWindowPos2i(Texts.leftPadding, drawable.getHeight() - 125);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.valueText5 + " " + Data.valueInWork5);
        */
        
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text1);
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 40);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text2);
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 60);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text3);
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 80);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text4);
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 100);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text5);
        gl.glWindowPos2i(drawable.getWidth() - Texts.rightPadding, drawable.getHeight() - 120);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, Texts.text6);

        framesCalc++;
        millis1 = System.currentTimeMillis();
        if (millis1 - millis0 >= 1000) {
            fps = framesCalc;
            millis0 = millis1;
            framesCalc = 0;
        }

        gl.glWindowPos2i(drawable.getWidth() - 90, 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "FPS:  " + fps);
//</editor-fold>
        Data.synchronize();
        Draw.calcsDraw();

        gl.glFlush();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
