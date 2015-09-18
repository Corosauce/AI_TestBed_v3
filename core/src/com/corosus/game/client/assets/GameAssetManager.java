package com.corosus.game.client.assets;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.corosus.game.Cst;
import com.corosus.game.client.render.IRenderable;
import com.corosus.game.client.render.WrappedAnim;
import com.corosus.game.client.render.WrappedSprite;

public enum GameAssetManager {
    INSTANCE;

    private HashMap<String, Animation>     animations = new HashMap<String, Animation>();
    private HashMap<String, Texture>       textures   = new HashMap<String, Texture>();
    private HashMap<String, TextureRegion> sprites    = new HashMap<String, TextureRegion>();
    private HashMap<String, Sound>         sounds     = new HashMap<String, Sound>();

    private HashMap<String, IRenderable>   cachedRenderable = new HashMap<String, IRenderable>();

    public void loadSounds(String jsonPath){
        Json json = new Json();
        json.addClassTag("sound", JSONSound.class);

        @SuppressWarnings("unchecked")
        HashMap<String, JSONSound> soundsJson = json.fromJson(HashMap.class, Gdx.files.internal(jsonPath));

        for (String name : soundsJson.keySet()) {
            JSONSound soundJson = soundsJson.get(name);

            Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundJson.soundFile));
            this.sounds.put(name, sound);
        }
    }

    public void loadSprites(String jsonPath){
        Json json = new Json();
        json.addClassTag("sprite", JSONSprite.class);

        @SuppressWarnings("unchecked")
		HashMap<String, JSONSprite> spritesJson = json.fromJson(HashMap.class, Gdx.files.internal(jsonPath));

        for (String name : spritesJson.keySet()) {
            JSONSprite spriteJson = spritesJson.get(name);

            if (!textures.containsKey(spriteJson.spriteTable))
                textures.put(spriteJson.spriteTable, new Texture(Gdx.files.internal(spriteJson.spriteTable)));

            Texture texture           = this.textures.get(spriteJson.spriteTable);
            TextureRegion[][] slice   = TextureRegion.split(texture, Cst.SPRITESIZE, Cst.SPRITESIZE);

            this.sprites.put(name, slice[spriteJson.row][spriteJson.column]);
        }
    }

    public void loadAnimations(String jsonPath){
        Json json = new Json();
        json.addClassTag("animation", JSONAnimation.class);

        @SuppressWarnings("unchecked")
		HashMap<String, JSONAnimation> animationsJson = json.fromJson(HashMap.class, Gdx.files.internal(jsonPath));

        for (String name : animationsJson.keySet()) {
            JSONAnimation animJson = animationsJson.get(name);

            if (!textures.containsKey(animJson.spriteTable))
                textures.put(animJson.spriteTable, new Texture(Gdx.files.internal(animJson.spriteTable)));

            Texture texture           = this.textures.get(animJson.spriteTable);
            TextureRegion[][] slice   = TextureRegion.split(texture, Cst.SPRITESIZE, Cst.SPRITESIZE);
            TextureRegion[]   row     = slice[animJson.row];
            TextureRegion[]   frames  = new TextureRegion[animJson.nframes];

            for (int i = 0; i < animJson.nframes; i++)
                frames[i] = row[i];

            Animation result = new Animation(animJson.duration, frames);

            this.animations.put(name, result);
        }
    }

    public Animation getAnimation(String key){
        return this.animations.get(key);
    }

    public TextureRegion getSprite(String key){
        return this.sprites.get(key);
    }

    public Sound getSound(String key){
        return this.sounds.get(key);
    }

    /* ======================================================== */

    private Animation getAnimationFromJson(JSONAnimation animJson){

        if (!textures.containsKey(animJson.spriteTable))
            textures.put(animJson.spriteTable, new Texture(Gdx.files.internal(animJson.spriteTable)));

        Texture texture           = this.textures.get(animJson.spriteTable);
        TextureRegion[][] slice   = TextureRegion.split(texture, Cst.SPRITESIZE, Cst.SPRITESIZE);
        TextureRegion[]   row     = slice[animJson.row];
        TextureRegion[]   frames  = new TextureRegion[animJson.nframes];

        for (int i = 0; i < animJson.nframes; i++)
            frames[i] = row[i + animJson.column];

        return new Animation(animJson.duration, frames);
    }

    private TextureRegion getTextureRegionFromJson(JSONSprite spriteJson){

        if (!textures.containsKey(spriteJson.spriteTable))
            textures.put(spriteJson.spriteTable, new Texture(Gdx.files.internal(spriteJson.spriteTable)));

        Texture texture           = this.textures.get(spriteJson.spriteTable);
        TextureRegion[][] slice   = TextureRegion.split(texture, Cst.SPRITESIZE, Cst.SPRITESIZE);

        return slice[spriteJson.row][spriteJson.column];
    }

    public HashMap<ActorState, HashMap<Orient, IRenderable>> getRenderAssets(String jsonPath){
        HashMap<ActorState, HashMap<Orient, IRenderable>> retVal = new HashMap<ActorState, HashMap<Orient, IRenderable>>();

        Json json = new Json();
        json.addClassTag("animation", JSONAnimation.class);
        json.addClassTag("sprite",    JSONSprite.class);

        @SuppressWarnings("unchecked")
        HashMap<String, JSONBaseRenderable> spritesJson = json.fromJson(HashMap.class, Gdx.files.internal(jsonPath));

        for (String key : spritesJson.keySet()){
            if (!this.cachedRenderable.containsKey(jsonPath + "$" + key)) {
                if (spritesJson.get(key) instanceof JSONAnimation)
                    cachedRenderable.put(jsonPath + "$" + key, new WrappedAnim(this.getAnimationFromJson((JSONAnimation) spritesJson.get(key))));

                if (spritesJson.get(key) instanceof JSONSprite)
                    cachedRenderable.put(jsonPath + "$" + key, new WrappedSprite(this.getTextureRegionFromJson((JSONSprite) spritesJson.get(key))));
            }

            String state  = key.split("\\$")[0];
            String orient = key.split("\\$")[1];

            if (!retVal.containsKey(ActorState.valueOf(state.toUpperCase())))
                retVal.put(ActorState.valueOf(state.toUpperCase()), new HashMap<Orient, IRenderable>());

            retVal.get(ActorState.valueOf(state.toUpperCase())).put(Orient.valueOf(orient.toUpperCase()), this.cachedRenderable.get(jsonPath + "$" + key));
        }

        return retVal;
    }
}
