package com.tar.iq.petcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.AllPetdataShowListBinding;
import com.tar.iq.petcare.databinding.MipetsAdapterBinding;
import com.tar.iq.petcare.interfaces.MyInterface;
import com.tar.iq.petcare.model.User;

import java.util.ArrayList;

public class MiPetsAdapter extends RecyclerView.Adapter<MiPetsAdapter.ClientViewHolder> {

    ArrayList<User> list;
    Context context;
    MyInterface myInterface;
    public MiPetsAdapter(Context context, ArrayList<User> list, MyInterface myInterface) {
        this.context=context;
        this.list = list;
            this.myInterface=myInterface;

    }


    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        MipetsAdapterBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.mipets_adapter,parent,false);
        return new ClientViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {

        User petInfo=list.get(position);
        holder.binding.setItem(petInfo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        MipetsAdapterBinding binding;
        public ClientViewHolder(@NonNull MipetsAdapterBinding allClientShowListBinding ) {
            super(allClientShowListBinding.getRoot());
            binding=allClientShowListBinding;

     /*       binding.cardView.setOnClickListener(view -> {
                myInterface.detailPet(list.get(getAdapterPosition()));
            });
*/
        }
    }
}
