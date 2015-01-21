package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import javafx.animation.Animation;
import javafx.scene.Scene;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Rectangle rect;
    Vector2 speed;
    boolean speedChanged;
    OrthographicCamera camera;
    int x = 0;
    int y = 0;
    private Scene stage;
    Sprite sprite;
    Sprite staticSprite;
    boolean fullscreen;
    Animation sonicAnimation;
    float elapsedTime;
    TextureRegion currentFrame;

    @Override
    public void create () {
        fullscreen = false;
        Gdx.graphics.setDisplayMode(1280, 720, fullscreen);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        rect = new Rectangle(50, 50, 100, 100);
        speed = new Vector2(4, 4);

        sprite = new Sprite(img);
//        sprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        sprite.setPosition(0, 0);
        sonicAnimation = new Animation(.065f, new TextureAtlas("SonicWalkCut-packed/pack.atlas").getRegions(), Animation.PlayMode.LOOP);
        elapsedTime = 0;
        staticSprite = new Sprite(new TextureRegion(sonicAnimation.getKeyFrame(elapsedTime)));
        camera = new OrthographicCamera();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.position.x = Gdx.graphics.getWidth()/2;
        camera.position.y = Gdx.graphics.getHeight()/2;
        camera.position.x = sprite.getX() + sprite.getWidth() / 2;
        camera.position.y = sprite.getY() + sprite.getHeight() / 2;
//      camera.translate(-Gdx.graphics.getWidth() /2, -Gdx.graphics.getHeight() / 2);

        currentFrame = sonicAnimation.getKeyFrame(elapsedTime);
    }
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.F) && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            fullscreen = !fullscreen;
            if(!fullscreen) {
                Gdx.graphics.setDisplayMode(1280, 720, fullscreen);
            } else  {

                Gdx.graphics.setDisplayMode(1920, 1080, fullscreen);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (speed.x == 4 && !speedChanged) {
                speed.x *= 3;
                speed.y *= 3;
                speedChanged = true;
            } else if (speed.x != 4 && speedChanged) {
                speed.x = 4;
                speed.y = 4;
                speedChanged = false;
            }
        }
        if (speed.x == 4) {
            System.out.println(4);
        }
        if (speed.x != 4) {
            System.out.println("not 4");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.setX(sprite.getX() + speed.x);
            camera.position.x = camera.position.x + speed.x;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.setX(sprite.getX() - speed.x);
            camera.position.x = camera.position.x - speed.x;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K)) {
            camera.zoom -= .01f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.L)) {
            camera.zoom += .01f;
        }
        if(!(rect.getX() <= 0)) {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                rect.setX(rect.getX() - speed.x);
                camera.position.x = camera.position.x - speed.x;
            }
        }
        if (!(rect.getX() + rect.getWidth() >= Gdx.graphics.getWidth())) {
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                rect.setX(rect.getX() + speed.x);
                camera.position.x = camera.position.x + speed.x;
            }
        }
        if(!(rect.getY() <= 0)) {
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                rect.setY(rect.getY() - speed.y);
                camera.position.y = camera.position.y- speed.y;
            }
        }
        if (!(rect.getY() + rect.getHeight() >= Gdx.graphics.getHeight())) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                rect.setY(rect.getY() + speed.y);
                camera.position.y = camera.position.y + speed.y;
            }
        }
        TextureRegion currentRegion = sonicAnimation.getKeyFrame(elapsedTime);
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(currentRegion != sonicAnimation.getKeyFrame(elapsedTime)) {
            staticSprite.setRegion(sonicAnimation.getKeyFrame(elapsedTime));
            staticSprite.setSize(sonicAnimation.getKeyFrame(elapsedTime).getRegionWidth() * 3, sonicAnimation.getKeyFrame(elapsedTime).getRegionHeight() * 3);
        }
        //camera.position.x = rect.getX();
        //camera.position.y = rect.getY();
        camera.zoom = MathUtils.clamp(camera.zoom, .1f, 1f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        staticSprite.draw(batch);
        //sprite.draw(batch);
        batch.draw(img, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportHeight = Gdx.graphics.getHeight();
//        camera.viewportWidth = Gdx.graphics.getWidth();
//        camera.position.x = Gdx.graphics.getWidth()/2;
//        camera.position.y = Gdx.graphics.getHeight()/2;
//        camera.update();
    }
}