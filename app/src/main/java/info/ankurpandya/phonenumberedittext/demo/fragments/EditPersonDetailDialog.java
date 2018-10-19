package info.ankurpandya.phonenumberedittext.demo.fragments;

import android.content.Context;
import android.helper.PhoneNumberEditText;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import info.ankurpandya.phonenumberedittext.demo.R;
import info.ankurpandya.phonenumberedittext.demo.entities.PersonDetail;
import info.ankurpandya.phonenumberedittext.demo.utils.Helper;

public class EditPersonDetailDialog extends DialogFragment {
    private static final String ARG_PERSON_DETAIL = "arg_person_detail";

    EditText edt_name;
    PhoneNumberEditText edt_phone;
    Button btn_save;

    private PersonDetail personDetail;

    private OnFragmentInteractionListener mListener;

    public EditPersonDetailDialog() {
        // Required empty public constructor
    }

    public static EditPersonDetailDialog newInstance(PersonDetail personDetail) {
        EditPersonDetailDialog fragment = new EditPersonDetailDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_DETAIL, personDetail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personDetail = (PersonDetail) getArguments().getSerializable(ARG_PERSON_DETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_person_detail_dialog, container, false);
        edt_name = rootView.findViewById(R.id.edt_name);
        edt_phone = rootView.findViewById(R.id.edt_phone);
        btn_save = rootView.findViewById(R.id.btn_save);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_name.setText(personDetail.getName());
        edt_phone.setPhoneNumber(personDetail.getPhoneNumber());
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

        personDetail.setName(edt_name.getText().toString());
        personDetail.setPhoneNumber(edt_phone.getPhoneNumber());
        mListener.onPersonDetailsUpdated(personDetail);
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onPersonDetailsUpdated(PersonDetail detail);
    }
}
