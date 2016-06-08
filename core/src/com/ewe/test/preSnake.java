package com.ewe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.Random;


public class preSnake extends ApplicationAdapter implements InputProcessor {

	Texture appleTex,headTex,bodyTex;
	private Stage stage;
	private ShapeRenderer lineDrawer;
    int trackNumber =10;
    int trackWidth;
    // next snake track
    int track;
    int step = 120;

    int margin =10;
    // to define game space and leave place for buttons
    int gameStartY, gameEndY;

    SnakeHead hSnake;
    SnakePart bSnake;
    Something apple;

    //debugg
    float minFPS = 100;
    float maxFPS = 0;
    float nowFPS;




	@Override
	public void create () {
        gameStartY = 100;
        gameEndY = Gdx.graphics.getHeight() - 10;
        headTex = new Texture(Gdx.files.internal("h.png"));
        bodyTex = new Texture(Gdx.files.internal("b.png"));
        appleTex = new Texture(Gdx.files.internal("a.png"));

        trackWidth = (int) (Gdx.graphics.getWidth() - 2*margin)/trackNumber;

        stage = new Stage(new ScreenViewport());
        // stage = new Stage(new StretchViewport(640, 480));
        // stage = new Stage(new FitViewport(640, 480));
        // stage = new Stage(new ExtendViewport(640, 480));
        // stage = new Stage(new ExtendViewport(640, 480, 800, 480));

        hSnake = new SnakeHead(headTex, gameEndY + 5);
        bSnake = new SnakePart(bodyTex, gameEndY + 5 + (int)hSnake.getHeight());
        apple = new Something(appleTex, true);

        stage.addActor(hSnake);
        stage.addActor(bSnake);
        stage.addActor(apple);

		lineDrawer = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);

	}

    @Override
    public void dispose() {
        stage.dispose();
        headTex.dispose();
        bodyTex.dispose();
        appleTex.dispose();
    }

	@Override
	public void render () {



		//check for collisions
        nowFPS = Gdx.graphics.getDeltaTime();
        if(nowFPS > maxFPS) {
            maxFPS = nowFPS;
            System.out.print("new max FPS: " + maxFPS + "\n");
        }
        if(nowFPS<minFPS){
            minFPS = nowFPS;
            System.out.print("new min FPS: " + minFPS + "\n");
        }
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update and draw actors

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
		//draw lines
		drawLines();
	}



	public void drawLines(){
		Gdx.gl.glLineWidth(2);
		lineDrawer.begin(ShapeRenderer.ShapeType.Line);
		lineDrawer.setColor(1, 1, 1, 1);
		for(int i = 0; i<=10;i++){
			lineDrawer.line(margin+i*trackWidth, gameStartY ,margin+i*trackWidth, gameEndY);
		}

		lineDrawer.end();


	}

	@Override
	public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.NUM_0){
            track = 0;
            hSnake.setNextX(track);
        }
		if (keycode == Input.Keys.NUM_1){
			track = 1;
            hSnake.setNextX(track);
		}
		if (keycode == Input.Keys.NUM_2){
			track = 2;
			hSnake.setNextX(track);
		}
		if (keycode == Input.Keys.NUM_3){
			track = 3;
			hSnake.setNextX(track);;
		}
		if (keycode == Input.Keys.NUM_4){
			track = 4;
			hSnake.setNextX(track);;
		}
		if (keycode == Input.Keys.NUM_5){
			track = 5;
			hSnake.setNextX(track);;
		}
		if (keycode == Input.Keys.NUM_6){
			track = 6;
			hSnake.setNextX(track);;
		}
		if (keycode == Input.Keys.NUM_7){
			track = 7;
			hSnake.setNextX(track);;
		}
		if (keycode == Input.Keys.NUM_8){
			track = 8;
			hSnake.setNextX(track);;
		}if (keycode == Input.Keys.NUM_9){
			track = 9;
			hSnake.setNextX(track);;
		}if(keycode == Input.Keys.DOWN)
            minFPS =100;


		Gdx.app.log("Track number", String.valueOf(track));
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

    public class Something extends Actor {
        Texture myTex;
        boolean isGood;
        Random random = new Random();

        public int posX,posY;


        public Something(Texture tex, boolean isGood){
            this.myTex = tex;
            this.isGood = isGood;
            this.posX = random.nextInt(trackNumber);
            this.posY = random.nextInt(gameEndY - myTex.getHeight()- gameStartY) + gameStartY;

            setBounds(posX,posY,myTex.getWidth(),myTex.getHeight());
            setTouchable(Touchable.enabled);

            Gdx.app.log("Somethinx x",String.valueOf(posX));
            Gdx.app.log("Somethinx y",String.valueOf(posY));

        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(myTex,margin + posX*trackWidth + (trackWidth - myTex.getWidth())/2,posY);
        }

        // TODO avoid to place where the snake is
        public void setNewPosition(){
            posX = random.nextInt(trackNumber);
            posY = random.nextInt(gameEndY - myTex.getHeight()- gameStartY) + gameStartY;
        }
    }

    public class SnakePart extends Actor {
        public Texture partTex;
        public int posX;
        public float posY;


        public SnakePart(Texture tex, int posY){
            this.partTex = tex;
            this.posX = 0;
            this.posY = posY;
            setBounds(posX,posY,partTex.getWidth(),partTex.getHeight());
            setTouchable(Touchable.enabled);

        }

        @Override
        public void act(float delta){
            posY -= step*delta;
            if(posY < gameStartY){
                posY = hSnake.posY+hSnake.getHeight();
                posX = hSnake.posX;
            }

        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(partTex,margin + posX*trackWidth + (trackWidth - partTex.getWidth())/2,posY);
        }


    }
    public class SnakeHead extends Actor {
        public Texture partTex;
        public int posX;
        public float posY;
        public int nextX;


        public SnakeHead(Texture tex, int posY){
            this.partTex = tex;
            this.posX = 0;
            this.nextX = posX;
            this.posY = posY;
            setBounds(posX,posY,partTex.getWidth(),partTex.getHeight());
            setTouchable(Touchable.enabled);

        }

        @Override
        public void act(float delta){
            posY -= step*delta;

            if ((posX == apple.posX) && (posY  < (apple.posY + apple.getHeight()))){
                Gdx.app.log("APPLE", String.valueOf(apple.posY));
                apple.setNewPosition();
            }

            if(posY < gameStartY){
                posY = gameEndY;
                posX = nextX;
            }
        }

        @Override
        public void draw(Batch batch, float alpha){
            batch.draw(partTex,margin + posX*trackWidth + (trackWidth - partTex.getWidth())/2,posY);
        }

        public void setNextX(int x){
            nextX = x;
        }


    }


}
