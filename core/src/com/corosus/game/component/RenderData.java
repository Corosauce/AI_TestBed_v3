package com.corosus.game.component;

import java.util.HashMap;

import com.artemis.Component;
import com.corosus.game.client.assets.ActorState;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.client.assets.Orient;
import com.corosus.game.client.render.IRenderable;

public class RenderData extends Component {

	public int renderType = 0;
	
	public Orient orient = Orient.UP;
	public ActorState state = ActorState.STATIC;
	public HashMap<ActorState, HashMap<Orient, IRenderable>> anims;
	
	public static int TYPE_SPRITE_ANIM = 0;
	public static int TYPE_LINE = 1;
	
	public RenderData() {
		
	}
	
	public RenderData(String spriteAsset) {
		setAsset(spriteAsset);
	}
	
	public void setAsset(String spriteAsset) {
		anims = GameAssetManager.instance().getRenderAssets("imgs/sprites/" + spriteAsset + ".json");
		orient = Orient.UP;
		state = ActorState.STATIC;
	}
	
}
