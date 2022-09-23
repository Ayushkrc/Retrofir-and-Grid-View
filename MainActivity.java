package com.training.gridretro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {


    private List<PostsResponse> postsResponsesList=new ArrayList<>();
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=findViewById(R.id.gridView);
        getAllPosts();
    }

    public void getAllPosts(){

        Call<List<PostsResponse>> postresponse=ApiClient.getInterface().getAllPost();
        postresponse.enqueue(new Callback<List<PostsResponse>>() {
            @Override
            public void onResponse(Call<List<PostsResponse>> call, Response<List<PostsResponse>> response) {
                if (response.isSuccessful()){

                    Toast.makeText(MainActivity.this,"sucessfull", Toast.LENGTH_SHORT).show();
                    postsResponsesList=response.body();
                    CustomAdapter customAdapter=new CustomAdapter(postsResponsesList,MainActivity.this);
                    gridView.setAdapter(customAdapter);
                }else {
                    Toast.makeText(MainActivity.this,"An error occured try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostsResponse>> call, Throwable t) {


                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomAdapter extends BaseAdapter{



        private List<PostsResponse> postsResponseList;
        private Context context;
        private LayoutInflater layoutInflater;


        public CustomAdapter(List<PostsResponse> postsResponseList, Context context) {
            this.postsResponseList = postsResponseList;
            this.context = context;
            this.layoutInflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override public int getCount() {
            return postsResponseList.size();
        }


        @Override public Object getItem(int position) {
            return null;
        }


        @Override public long getItemId(int position) {
            return 0;
        }


        @Override public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=layoutInflater.inflate(R.layout.row_grid,parent,false);
            }

            TextView tv_name = convertView.findViewById(R.id.tv_name);
            TextView tv_id = convertView.findViewById(R.id.tv_id);
            TextView tv_website = convertView.findViewById(R.id.tv_website);
            TextView tv_phone = convertView.findViewById(R.id.tv_phone);
            TextView tv_city = convertView.findViewById(R.id.tv_city);
            TextView tv_company = convertView.findViewById(R.id.tv_company);



            tv_name.setText(postsResponseList.get(position).getName());
            tv_id.setText(postsResponseList.get(position).getId());
            tv_website.setText(postsResponseList.get(position).getWebsite());
            tv_phone.setText(postsResponseList.get(position).getPhone());
            tv_city.setText(postsResponsesList.get(position).getAddress().getCity());
            tv_company.setText(postsResponseList.get(position).getCompanyDetails().getName());



            return convertView;
        }
    }



}