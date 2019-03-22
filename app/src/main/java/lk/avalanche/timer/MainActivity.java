package lk.avalanche.timer;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import lk.avalanche.timer.ListContent.SoundContent;
import lk.avalanche.timer.db.DataRepository;
import lk.avalanche.timer.db.Entity.Data;
import lk.avalanche.timer.ui.main.MainFragment;
import lk.avalanche.timer.ui.main.SoundFragment;

public class MainActivity extends AppCompatActivity implements SoundFragment.OnListFragmentInteractionListener {
    DataRepository dataRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        dataRepository = new DataRepository(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Navigation.findNavController(this,R.id.main).navigate(R.id.action_main_to_setting);
                //startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(SoundContent.SoundItem item) {
        MediaPlayer player = MediaPlayer.create(this, getResourceId(item.content, "raw", getPackageName()));
        player.start();
        Data initialData = dataRepository.getInitialData();
        if (item.type) {
            initialData.setBellSound(item.content);
        } else {
            initialData.setCountDownSound(item.content);
        }
        dataRepository.updateData(initialData);
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
