package ml.qingsu.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.directloadtest.R;
import com.example.directloadtest.R.id;
import com.example.directloadtest.R.layout;

import java.util.Collections;
import java.util.List;


public class MusicJava extends Activity {
	public int page=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(layout.music_activity);


		findViewById(R.id.music_button2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 新增
				startActivity(new Intent(MusicJava.this, MusiclookforJava.class));
			}
		});
		this.Get();
	}
	private void Get() {
		Tools.getmusic(this, this.page, new Tools.FindMusicCallBack() {
			@Override
			public void finished(List<MusicNode> results) {
				if(results!=null)
				{
					ListView lv=(ListView) MusicJava.this.findViewById(id.music_listview);
					Collections.reverse(results);
					lv.setAdapter(new Music_Adapter(MusicJava.this, results));

				}
			}
		});
	}
}
