package ru.dimasokol.school.passwordlayout;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mResultTextView;
    private EditText mSourceTextView;
    private View mCopyButton;

    private PasswordsHelper mHelper;

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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
