package com.example.kelvin.demomenu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.kelvin.demomenu.entities.Benefit;

/**
 * Created by kelvin on 30/09/16.
 */

public class RelatedBenefitFragment extends Fragment {

    private static final String TAG = "RelatedBenefitFragment";
    Benefit benefit;

    public static final RelatedBenefitFragment newInstance(Benefit benefit) {
        RelatedBenefitFragment f = new RelatedBenefitFragment();
        f.benefit = benefit;

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.benefit_fragment_page, container, false);

        TextView txtBenefit = (TextView) view.findViewById(R.id.txtBenefit);
        txtBenefit.setText(benefit.getTitulo());

        TextView txtChapita = (TextView) view.findViewById(R.id.txtChapita);
        txtChapita.setText(benefit.getChapita());

        ImageLoader imageLoader = ClubRequestManager.getInstance(getContext()).getImageLoader();

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.image);
        imageView.setImageUrl(benefit.getPathLogo(), imageLoader);


       /* imageLoader.get(benefit.getPathLogo(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {

                    ImageView imageView = (ImageView) view.findViewById(R.id.image);
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                // Log.i(TAG, error.getLocalizedMessage());
            }
        });*/

        return view;
    }
}
