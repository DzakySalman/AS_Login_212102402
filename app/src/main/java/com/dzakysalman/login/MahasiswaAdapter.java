package com.dzakysalman.login;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaViewHolder> implements Filterable {
    private List<MahasiswaModel> _mahasiswaModelList;
    private List<MahasiswaModel> _mahasiswaModelListFull;

    public MahasiswaAdapter(List<MahasiswaModel> mahasiswaModelList) {
        this._mahasiswaModelList = mahasiswaModelList;
        _mahasiswaModelListFull = new ArrayList<>(mahasiswaModelList);
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_mahasiswa, parent, false);
        return new MahasiswaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        MahasiswaModel mm = _mahasiswaModelList.get(position);

        holder._jkImageView.setImageResource(R.drawable.boy);

        if (mm.getJenisKelamin().toLowerCase().equals("perempuan")) {
            holder._jkImageView.setImageResource(R.drawable.girl);
        }

        holder._nimTextView.setText(mm.getNIM());
        holder._namaTextView.setText(mm.getNama());
        holder._jkTextView.setText(mm.getJenisKelamin());

        String jp = mm.getJP();
        jp = jp.substring(0, 2);
        holder._jpTextView.setText(jp);

        // Set Warna Beda Prodi
        if (jp.equals("TI")) {
            holder._jpTextView.setBackgroundColor(Color.BLUE);
        } else if (jp.equals("SI")) {
            holder._jpTextView.setBackgroundColor(Color.RED);
        } else {
            holder._jpTextView.setBackgroundColor(Color.GRAY);
        }

        // Set record number
        holder._tvRecordNumber.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return _mahasiswaModelList != null ? _mahasiswaModelList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return mahasiswaFilter;
    }

    private Filter mahasiswaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MahasiswaModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(_mahasiswaModelListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MahasiswaModel item : _mahasiswaModelListFull) {
                    if (item.getNama().toLowerCase().contains(filterPattern) || item.getNIM().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            _mahasiswaModelList.clear();
            _mahasiswaModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
