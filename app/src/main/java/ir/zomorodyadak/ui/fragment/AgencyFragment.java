package ir.zomorodyadak.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ir.zomorodyadak.R;
import ir.zomorodyadak.model.Agency.AgencyBodyModel;
import ir.zomorodyadak.model.Agency.AgencyResponseModel;
import ir.zomorodyadak.server.ApiUtil;
import ir.zomorodyadak.ui.dialog.MessageViewDialog;
import ir.zomorodyadak.ui.widget.erroable_edittext.CustomEditText;
import ir.zomorodyadak.ui.widget.CustomToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AgencyFragment extends Fragment implements Callback<AgencyResponseModel> {

    private CustomEditText edtName, edtCode, edtPhone, edtProvince, edtCity;
    private MaterialButton btnSubmit;
    private Dialog progressDialog;
    private Activity activity;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency, container, false);
        edtName = view.findViewById(R.id.edt_agency_name);
        edtCode = view.findViewById(R.id.edt_agency_code_meli);
        edtPhone = view.findViewById(R.id.edt_agency_phone);
        edtProvince = view.findViewById(R.id.edt_agency_province);
        edtCity = view.findViewById(R.id.edt_agency_city);
        btnSubmit = view.findViewById(R.id.btn_agency_submit);
        setupViews();
        return view;
    }

    private void setupViews() {
        edtName.setImageDrawable(R.drawable.ic_person_outline_black_24dp);
        edtCode.setImageDrawable(R.drawable.ic_assignment_ind_black_24dp);
        edtPhone.setImageDrawable(R.drawable.ic_phone_black_24dp);
        edtProvince.setImageDrawable(R.drawable.ic_location_city_black_24dp);
        edtCity.setImageDrawable(R.drawable.ic_location_city_black_24dp);

        edtName.setInputType(CustomEditText.NAME_INPUT);
        edtCode.setInputType(CustomEditText.CODE_INPUT);
        edtPhone.setInputType(CustomEditText.PHONE_INPUT);
        edtProvince.setInputType(CustomEditText.PROVINCE_INPUT);
        edtCity.setInputType(CustomEditText.CITY_INPUT);
        validate();
        setupButton();
    }


    private void validate() {
        edtName.onTextWatcher((s, start, before, count) -> {
            if (s.length() < 5) {
                edtName.setErrorEnabled("لطفا مقدار را صحیح وارد نمایید");
            } else if (s.length() == 5 || s.length() == 0) {
                edtName.setErrorDisabled();
            }
        });
        edtCode.onTextWatcher((s, start, before, count) -> {
            edtName.clearFocus();
            if (s.length() < 10) {
                edtCode.setErrorEnabled("کدملی صحیح وارد نشده است");
            } else if (s.length() == 10 || s.length() == 0) {
                edtCode.setErrorDisabled();
                edtProvince.requestFocus();
            }
        });
        edtProvince.onTextWatcher((s, start, before, count) -> {
            edtCode.clearFocus();
            if (s.length() < 3) {
                edtProvince.setErrorEnabled("لطفا مقدار را صحیح وارد نمایید");
            } else if (s.length() == 3 || s.length() == 0) {
                edtProvince.setErrorDisabled();
            }
        });
        edtCity.onTextWatcher((s, start, before, count) -> {
            edtProvince.clearFocus();
            if (s.length() < 3) {
                edtCity.setErrorEnabled("لطفا مقدار را صحیح وارد نمایید");
            } else if (s.length() == 3 || s.length() == 0) {
                edtCity.setErrorDisabled();
            }
        });
        edtPhone.onTextWatcher((s, start, before, count) -> {
            edtCity.clearFocus();
            if (s.length() < 11) {
                edtPhone.setErrorEnabled("شماره صحیح وارد نشده است");
            } else if (s.length() == 11 || s.length() == 0) {
                edtPhone.setErrorDisabled();
                hideKeyboard();
                Objects.requireNonNull(getView()).clearFocus();
            }
        });
    }

    private void setupDialog() {
        progressDialog = new Dialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        if (progressDialog.getWindow() != null)
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setupButton() {
        setupDialog();
        btnSubmit.setOnClickListener(view -> {
            if (register()) {
                progressDialog.show();
                ApiUtil.getserviceClassSecond().addAgency(new AgencyBodyModel(edtName.getText(),
                        edtCode.getText(), edtProvince.getText(), edtCity.getText(),
                        edtPhone.getText())).enqueue(AgencyFragment.this);
            } else {
                MessageViewDialog dialog = new MessageViewDialog(activity,
                        context.getString(R.string.agencyErrorDialogMessage), "خطا!");
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    private boolean register() {
        return edtName.getEdtLength() > 4 && edtCode.getEdtLength() == 10 &&
                edtProvince.getEdtLength() > 2 && edtCity.getEdtLength() > 2 &&
                edtPhone.getEdtLength() == 11;
    }

    public void hideKeyboard() {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    activity.getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager
                        .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                                0);
            }
        }
    }

    @Override
    public void onResponse(Call<AgencyResponseModel> call, Response<AgencyResponseModel> response) {
        progressDialog.dismiss();
        if (response.isSuccessful()) {
            AgencyResponseModel model = response.body();
            if (model != null) {
                if (model.getResponse().equals("true")) {
                    MessageViewDialog dialog = new MessageViewDialog(activity,
                            context.getString(R.string.agencySuccesfulMessage),
                            "تبریک!");
                    dialog.setCancelable(false);
                    dialog.show();
                } else {
                    MessageViewDialog dialog = new MessageViewDialog(activity,
                            context.getString(R.string.agency_user_exists),
                            "خطا!");
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else {
                CustomToast.createToast("مشکلی در اتصال به سرور پیش امده است", activity);
            }
        }
    }

    @Override
    public void onFailure(Call<AgencyResponseModel> call, Throwable t) {
        Log.e("AgencyFragment", "onFailure: " + t.getMessage());
        progressDialog.dismiss();
        /*CustomToast.createToast("مشکلی در ارتباط با سرور پیش آمده. لطفا بعدا امتحان کنید!",
                activity);*/
        CustomToast.createToast(t.getMessage(),
                activity);
    }
}
