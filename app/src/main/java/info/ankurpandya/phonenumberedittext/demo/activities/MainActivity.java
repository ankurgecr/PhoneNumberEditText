package info.ankurpandya.phonenumberedittext.demo.activities;

import android.app.Activity;
import android.helper.PhoneNumberEditText;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import info.ankurpandya.phonenumberedittext.demo.R;
import info.ankurpandya.phonenumberedittext.demo.adapters.PersonListAdapter;
import info.ankurpandya.phonenumberedittext.demo.entities.PersonDetail;
import info.ankurpandya.phonenumberedittext.demo.fragments.EditPersonDetailDialog;
import info.ankurpandya.phonenumberedittext.demo.utils.Helper;

public class MainActivity extends AppCompatActivity
        implements EditPersonDetailDialog.OnFragmentInteractionListener {

    PersonListAdapter adapter;
    EditText edt_name;
    PhoneNumberEditText edt_phone;
    Button btn_save;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        btn_save = findViewById(R.id.btn_save);
        list = findViewById(R.id.list);

        adapter = new PersonListAdapter(new PersonListAdapter.PersonDetailHandler() {
            @Override
            public void onEditRequested(PersonDetail personDetail) {
                showEditPersonDetailDialog(personDetail);
            }
        });
        list.setAdapter(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePersonDetails();
            }
        });
    }

    private void savePersonDetails() {
        if (!Helper.isValid(edt_name.getText())) {
            edt_name.setError("Invalid");
            edt_name.requestFocus();
            return;
        }
        if (!edt_phone.isValid()) {
            return;
        }

        adapter.insertPerson(
                edt_name.getText().toString(),
                edt_phone.getPhoneNumber()
        );
        clearDetails();
        hideKeyboard(this);
    }

    private void clearDetails() {
        edt_name.setText("");
        edt_phone.setPhoneNumber("");
        edt_name.requestFocus();
    }

    private void showEditPersonDetailDialog(PersonDetail personDetail) {
        EditPersonDetailDialog dialog = EditPersonDetailDialog.newInstance(personDetail);
        dialog.show(getSupportFragmentManager(), "Edit Person detail");
    }

    @Override
    public void onPersonDetailsUpdated(PersonDetail detail) {
        adapter.updatePerson(detail);
        hideKeyboard(this);
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
