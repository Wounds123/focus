package com.rance.sectormenu;

import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rance.library.ButtonData;
import com.rance.library.ButtonEventListener;
import com.rance.library.SectorMenuButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Rance on 2016/11/10 16:41
 * 邮箱：rance935@163.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_view);
        initBottomSectorMenuButton();

    }



    private void initBottomSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.bottom_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.like, R.mipmap.mark,
                R.mipmap.search, R.mipmap.copy};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    private void setListener(final SectorMenuButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                showToast("button" + index);
            }

            @Override
            public void onExpand() {
                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
                showToast("onCollapse");
            }
        });
    }

    private void showToast(String text) {
        //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
