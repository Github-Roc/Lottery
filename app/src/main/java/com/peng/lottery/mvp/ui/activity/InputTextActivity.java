package com.peng.lottery.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import android.text.Selection;
import android.text.TextUtils;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;

public class InputTextActivity extends SimpleBaseActivity {

    @BindView(R.id.et_input_content)
    MaterialEditText etInputContent;
    @BindView(R.id.bt_input_ok)
    MaterialButton btInputOk;

    public static final String INPUT_TITLE = "title";
    public static final String INPUT_TEXT = "content";
    private String title;
    private String content;

    @Override
    protected void init() {
        super.init();

        Intent intent = getIntent();
        title = intent.getStringExtra(INPUT_TITLE);
        content = intent.getStringExtra(INPUT_TEXT);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_input_text;
    }

    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(title);

        if (!TextUtils.isEmpty(content)) {
            etInputContent.setText(content);
            Selection.setSelection(etInputContent.getText(), content.length());
        }
    }

    @Override
    protected void initListener() {
        btInputOk.setOnClickListener(v -> {
            String content = etInputContent.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra(INPUT_TEXT, content);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    public static void start(Activity activity, String inputTitle, String inputContent, int inputCode) {
        Intent intent = new Intent(activity, InputTextActivity.class);
        intent.putExtra(INPUT_TITLE, inputTitle);
        intent.putExtra(INPUT_TEXT, inputContent);
        activity.startActivityForResult(intent, inputCode);
    }
}
