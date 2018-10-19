package info.ankurpandya.phonenumberedittext.demo.adapters;

import android.helper.PhoneNumberEditText;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.ankurpandya.phonenumberedittext.demo.R;
import info.ankurpandya.phonenumberedittext.demo.entities.PersonDetail;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private List<PersonDetail> personDetailList;
    private PersonDetailHandler handler;

    public PersonListAdapter(PersonDetailHandler handler) {
        this.personDetailList = new ArrayList<>();
        this.handler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(personDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return personDetailList.size();
    }

    public void insertPerson(String name, String contact) {
        String id = UUID.randomUUID().toString();
        insertPerson(new PersonDetail(id, name, contact));
    }

    public void insertPerson(PersonDetail personDetail) {
        if (personDetailList.contains(personDetail)) {
            removePerson(personDetail);
        }
        int position = personDetailList.size();
        personDetailList.add(personDetail);
        notifyItemInserted(position);
    }

    public void removePerson(PersonDetail personDetail) {
        int position = personDetailList.indexOf(personDetail);
        if (position >= 0) {
            personDetailList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updatePerson(PersonDetail personDetail) {
        int position = personDetailList.indexOf(personDetail);
        personDetailList.set(position, personDetail);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView txt_name;
        TextView txt_contact;
        View btn_edit;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txt_name = mView.findViewById(R.id.txt_name);
            txt_contact = mView.findViewById(R.id.txt_contact);
            btn_edit = mView.findViewById(R.id.btn_edit);
        }

        public void bind(final PersonDetail personDetail) {
            txt_name.setText(personDetail.getName());
            txt_contact.setText(
                    PhoneNumberEditText.getPrintableMobileNumber(
                            personDetail.getPhoneNumber()
                    )
            );
            if (handler != null) {
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onEditRequested(personDetail);
                    }
                });
            }
        }
    }

    public interface PersonDetailHandler {
        void onEditRequested(PersonDetail personDetail);
    }
}
