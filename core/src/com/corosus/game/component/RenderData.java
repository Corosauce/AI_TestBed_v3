package com.corosus.game.component;

import java.util.HashMap;

import com.artemis.Component;
import com.corosus.game.client.assets.ActorState;
import com.corosus.game.client.assets.GameAssetManager;
import com.corosus.game.client.assets.Orient;
import com.corosus.game.client.render.IRenderable;

public class RenderData extends Component {

	public Orient orient = Orient.UP;
	public ActorState state = ActorState.STATIC;
	public HashMap<ActorState, HashMap<Orient, IRenderable>> anims;
	
	public RenderData() {
		
	}
	
	public RenderData(String spriteAsset) {
		anims = GameAssetManager.INSTANCE.getRenderAssets("imgs/sprites/" + spriteAsset + ".json");
	}
	
}
