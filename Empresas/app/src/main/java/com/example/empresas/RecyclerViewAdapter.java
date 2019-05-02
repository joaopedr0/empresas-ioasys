package com.example.empresas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EmpresaViewHolder> {

    private View.OnClickListener onItemClickListener;
    private List<Empresa> listaEmpresas;
    private Context context;

    class EmpresaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemEmpresa;
        TextView nomeEmpresa;
        TextView tipoEmpresa;
        TextView paisEmpresa;

        EmpresaViewHolder(View itemView) {
            super(itemView);
            imagemEmpresa = itemView.findViewById(R.id.imagem_empresa_card);
            nomeEmpresa = itemView.findViewById(R.id.nome_empresa);
            tipoEmpresa = itemView.findViewById(R.id.tipo_empresa);
            paisEmpresa = itemView.findViewById(R.id.pais_empresa);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

    RecyclerViewAdapter(List<Empresa> listaEmpresa, Context context) {
        this.listaEmpresas = listaEmpresa;
        this.context = context;
    }

    @NonNull
    @Override
    public EmpresaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view,
                parent, false);
        RecyclerView.ViewHolder holder = new EmpresaViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view);
            }
        });
        return new EmpresaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaViewHolder holder, int position) {
        Empresa currentItem = listaEmpresas.get(position);

        if(currentItem.getPhoto() != null){
            Glide.with(context)
                    .load("http://empresas.ioasys.com.br" + currentItem.getPhoto())
                    .into(holder.imagemEmpresa);
        } else {
            holder.imagemEmpresa.setImageResource(R.drawable.ic_photo);
        }
        if(currentItem.getName() != null){
            holder.nomeEmpresa.setText(currentItem.getName());
        }
        if(currentItem.getTipoEmpresa() != null){
            holder.tipoEmpresa.setText(currentItem.getTipoEmpresa().getTipoEmpresaString());
        }
        if(currentItem.getCountry() != null){
            holder.paisEmpresa.setText(currentItem.getCountry());
        }
    }

    @Override
    public int getItemCount() {
        return listaEmpresas.size();
    }

    public void setClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }

}
