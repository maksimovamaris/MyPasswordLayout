package ru.dimasokol.school.passwordlayout;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mResultTextView;
    private EditText mSourceTextView;
    private View mCopyButton;
    private ImageView mQuality;
    private TextView mQualityTextView;
    private CompoundButton mUseUppercase;
    private CompoundButton mUseDigits;
    private CompoundButton mUseChars;
    private SeekBar mLevelBar;
    private PasswordsHelper mHelper;
    private TextView mLengthView;
    private Button mGenerateButton;
    private TextView mTextGen;
    private View mCopyGenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new PasswordsHelper(
                getResources().getStringArray(R.array.russian),
                getResources().getStringArray(R.array.english)
        );

        mResultTextView = findViewById(R.id.result_text);
        mSourceTextView = findViewById(R.id.source_text);
        mCopyButton = findViewById(R.id.button_copy);
        mQuality = findViewById(R.id.quality);
        mQualityTextView = findViewById(R.id.quality_text);

        //Checkboxes

        mUseUppercase = findViewById(R.id.check_uppercase);
        mUseDigits=findViewById(R.id.check_digits);
        mUseChars=findViewById(R.id.check_char);

        mCopyButton.setEnabled(false);

        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText(
                        MainActivity.this.getString(R.string.clipboard_title),
                        mResultTextView.getText()
                ));

                Toast.makeText(MainActivity.this, R.string.message_copied, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mSourceTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mResultTextView.setText(mHelper.convert(s));
                mCopyButton.setEnabled(s.length() > 0);
                int quality = mHelper.getQuality(s);
                mQuality.setImageLevel(quality*2000);
                mQualityTextView.setText(getResources().getStringArray(R.array.qualities)[quality]);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
        mTextGen=findViewById(R.id.result_text_gen);
        mLevelBar = findViewById(R.id.seek_level);
        mLengthView= findViewById(R.id.length);
        mLevelBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
//                mDemoDrawableView.setImageLevel(progress);
                String chars = getResources().getQuantityString(R.plurals.chars,progress ,progress,progress+8,"Длина :");
//                getResources().getQuantityString(R.plurals.test3, success, success, total, group)


//                String chars=getResources().getQuantityString(R.plurals.test3, progress, progress,10 , "5 tests out of 10 ok");
                String chars_sum = getResources().getQuantityString(R.plurals.chars, 8+progress);
                mLengthView.setText(chars);
//                mLengthView.setText("Длина: 8 + ");
//                mLengthView.setText(charTest);
//                        + progress+" "+ chars+" = " +(8+progress) +" "+chars_sum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
mGenerateButton= findViewById(R.id.button);
mGenerateButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mTextGen.setText(mHelper.gen_password(mLevelBar.getProgress()+8,mUseUppercase.isChecked(),mUseDigits.isChecked(),mUseChars.isChecked()));
    }
});
mCopyGenButton=findViewById(R.id.button_copy_gen);
mCopyGenButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(
                MainActivity.this.getString(R.string.clipboard_title),
                mResultTextView.getText()
        ));
        Toast.makeText(MainActivity.this, R.string.message_copied, Toast.LENGTH_SHORT)
                .show();
    }
});
    }
}
