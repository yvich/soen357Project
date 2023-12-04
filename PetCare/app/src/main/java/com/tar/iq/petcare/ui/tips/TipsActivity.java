package com.tar.iq.petcare.ui.tips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tar.iq.petcare.R;
import com.tar.iq.petcare.databinding.ActivityResourceBinding;
import com.tar.iq.petcare.databinding.ActivityTipsBinding;

import java.util.Objects;

public class TipsActivity extends AppCompatActivity {
    ActivityTipsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tips);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFE4E1"));
        }
        Intent intent = getIntent();
        String value = intent.getStringExtra("kind");
        binding.title.setText(value);
        binding.feedingtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value.equals("Cat")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vcacanada.com/know-your-pet/nutrition-feeding-guidelines-for-cats"));
                    startActivity(browserIntent);
                }else if(value.equals("Dog")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vcacanada.com/know-your-pet/nutrition-general-feeding-guidelines-for-dogs"));
                    startActivity(browserIntent);
                }
                else if(value.equals("Bird")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://petsandvets.ca/files/2017/02/feeding-birds.pdf"));
                    startActivity(browserIntent);
                }
                else if(value.equals("Reptile")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vcacanada.com/know-your-pet/feeding-rodents"));
                    startActivity(browserIntent);
                }

            }
        });
        binding.health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value.equals("Cat")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://veterinarypartner.vin.com/default.aspx?pid=19239&catId=102887&ind=580"));
                    startActivity(browserIntent);
                }else if(value.equals("Dog")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://veterinarypartner.vin.com/default.aspx?pid=19239&catId=102888&ind=1"));
                    startActivity(browserIntent);
                }
                else if(value.equals("Bird")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://veterinarypartner.vin.com/default.aspx?pid=19239&catId=102889&ind=1469"));
                    startActivity(browserIntent);
                }
                else if(value.equals("Reptile")){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.rspca.org.uk/adviceandwelfare/pets/rodents"));
                    startActivity(browserIntent);
                }

            }
        });
        binding.grooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TipsActivity.this);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.bmi_info_dialog);
                TextView no = dialog.findViewById(R.id.no);
                TextView show = dialog.findViewById(R.id.textshow);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(value.equals("Dog")){
                    show.setText("Nutrition - General Feeding Guidelines for Dogs | VCA Animal Hospital\n" +
                            "Curious about what to feed your pet dog? There are several considerations to make when it comes to their diet. Visit vcahospitals.com for expert advice.\n" +
                            "VcaCanada\n" +
                            "Grooming and Coat Care for Your Dog | VCA Canada Animal Hospitals\n" +
                            "The general condition of your dog's skin and coat are good indicators of its health. Although health and nutrition influence the luster and texture of your pet's coat from the inside, regular grooming and skin care on the outside will help keep your dog's coat clean and free of tangles, no matter what type of hair coat he or she has.\n" +
                            "VcaCanada");

                }else if(value.equals("Cat")){
                    show.setText("Nutrition - General Feeding Guidelines for Dogs | VCA Animal Hospital\n" +
                            "Curious about what to feed your pet cat? There are several considerations to make when it comes to their diet. Visit vcahospitals.com for expert advice.\n" +
                            "VcaCanada\n" +
                            "Grooming and Coat Care for Your cat | VCA Canada Animal Hospitals\n" +
                            "The general condition of your dog's skin and coat are good indicators of its health. Although health and nutrition influence the luster and texture of your pet's coat from the inside, regular grooming and skin care on the outside will help keep your cat's coat clean and free of tangles, no matter what type of hair coat he or she has.\n" +
                            "VcaCanada\n");
                }
                else if(value.equals("Bird")){
                    {
                        show.setText("Nutrition - General Feeding Guidelines for Dogs | VCA Animal Hospital\n" +
                                "Curious about what to feed your pet brid? There are several considerations to make when it comes to their diet. Visit vcahospitals.com for expert advice.\n" +
                                "VcaCanada\n" +
                                "Grooming and Coat Care for Your bird | VCA Canada Animal Hospitals\n" +
                                "The general condition of your bird's skin and coat are good indicators of its health. Although health and nutrition influence the luster and texture of your pet's coat from the inside, regular grooming and skin care on the outside will help keep your bird's coat clean and free of tangles, no matter what type of hair coat he or she has.\n" +
                                "VcaCanada\n");
                    }
                }
                else if(value.equals("Reptile")){
                    {
                        show.setText("Nutrition - General Feeding Guidelines for Dogs | VCA Animal Hospital\n" +
                                "Curious about what to feed your pet Reptile? There are several considerations to make when it comes to their diet. Visit vcahospitals.com for expert advice.\n" +
                                "VcaCanada\n" +
                                "Grooming and Coat Care for Your Reptile | VCA Canada Animal Hospitals\n" +
                                "The general condition of your dog's skin and coat are good indicators of its health. Although health and nutrition influence the luster and texture of your pet's coat from the inside, regular grooming and skin care on the outside will help keep your Reptile's coat clean and free of tangles, no matter what type of hair coat he or she has.\n" +
                                "VcaCanada\n");
                    }
                }
                dialog.show();

            }
        });

    }
}