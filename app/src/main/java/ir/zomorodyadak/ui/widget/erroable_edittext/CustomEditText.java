package ir.zomorodyadak.ui.widget.erroable_edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import ir.zomorodyadak.R;

public class CustomEditText extends RelativeLayout implements TextWatcher {
    public static int NAME_INPUT = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
    public static int CODE_INPUT = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL;
    public static int PHONE_INPUT = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PHONETIC;
    public static int PROVINCE_INPUT = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
    public static int CITY_INPUT = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;

    private int maxLength;
    private AppCompatTextView tvHint, tvError;
    private AppCompatEditText editText;
    private String digits;


    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        String edittextHint = a.getString(R.styleable.CustomEditText_hint);
        maxLength = a.getInt(R.styleable.CustomEditText_maxLength, 12);
        digits = a.getString(R.styleable.CustomEditText_digit);
        a.recycle();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.agency_custom_edittext,
                this, true);
        editText = view.findViewById(R.id.edt_custom);
        tvHint = view.findViewById(R.id.tv_custom_edittext_hint);
        tvError = view.findViewById(R.id.tv_custom_edt_error);
        editText.addTextChangedListener(this);
        if (edittextHint != null)
            editText.setHint(edittextHint);
        setMaxLength();
        setDigits();
    }

    private void setDigits() {
        if (digits != null)
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    public int getEdtLength() {
        if (editText.getText() != null)
            return editText.getText().toString().length();
        else
            return 0;
    }

    public void setImageDrawable(@DrawableRes int imageDrawable) {
        editText.setCompoundDrawablesWithIntrinsicBounds(imageDrawable, 0, 0,
                0);
    }

    public void setMaxLength() {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);
    }

    public String getText() {
        if (editText.getText() != null)
            return editText.getText().toString();
        else
            return null;
    }

    public void onTextWatcher(CustomEditTextTextWatcher textTextWatcherListener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textTextWatcherListener.onTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean isErrorEnabled() {
        return tvError.getVisibility() == VISIBLE;
    }

    public int getErrorVisibility() {
        return tvError.getVisibility();
    }

    public void setErrorEnabled(@StringRes int errorText) {
        tvError.setVisibility(AppCompatTextView.VISIBLE);
        tvError.setText(errorText);
        YoYo.with(Techniques.FadeIn).duration(500).playOn(tvHint);
    }

    public void setErrorEnabled(String errorText) {
        tvError.setVisibility(AppCompatTextView.VISIBLE);
        tvError.setText(errorText);
    }

    public void setErrorDisabled() {
        YoYo.with(Techniques.FadeOut).duration(500).playOn(tvError);
        tvError.setVisibility(AppCompatTextView.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 1) {
            if (tvHint.getVisibility() != AppCompatTextView.VISIBLE) {
                tvHint.setText(editText.getHint());
                tvHint.setVisibility(AppCompatTextView.VISIBLE);
                YoYo.with(Techniques.SlideInDown).duration(500).playOn(tvHint);
            }
        }
        if (s.length() == 0 && tvHint.getVisibility() == AppCompatTextView.VISIBLE) {
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setDuration(500);
            tvHint.setVisibility(AppCompatTextView.INVISIBLE);
            tvHint.startAnimation(animation);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
