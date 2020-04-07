package sondo65.com.androidtest.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sondo65.com.androidtest.R;
import sondo65.com.androidtest.models.City;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder> implements
        ListPreloader.PreloadModelProvider<String> {

    private List<City> mListCity = new ArrayList<>();
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

    public CityRecyclerAdapter(ViewPreloadSizeProvider<String> viewPreloadSizeProvider){
        this.preloadSizeProvider = viewPreloadSizeProvider;
    }

    public void replaceData(List<City> data) {
        mListCity.clear();
        mListCity.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cities_list_item,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {

        holder.name.setText(mListCity.get(position).getName());
        holder.country.setText(mListCity.get(position).getCountry());
        holder.population.setText(mListCity.get(position).getPopulation());
    }

    @Override
    public int getItemCount() {
        if(mListCity != null)
            return mListCity.size();
        return 0;
    }

    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String name = mListCity.get(position).getName();
        if(TextUtils.isEmpty(name)){
            return Collections.emptyList();
        }
        return Collections.singletonList(name);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return null;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{

        TextView name,country,population;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            country = itemView.findViewById(R.id.tv_country);
            population = itemView.findViewById(R.id.tv_population);
        }
    }
}
