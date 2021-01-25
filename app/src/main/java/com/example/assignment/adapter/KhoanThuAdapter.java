package com.example.assignment.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment.R;
import com.example.assignment.dao.DaoGiaoDich;
import com.example.assignment.dao.DaoThuChi;
import com.example.assignment.model.GiaoDich;
import com.example.assignment.model.ThuChi;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.ViewHolder> {
    private Context context;
    public  ArrayList<GiaoDich> list;
    private DaoGiaoDich daoGiaoDich;
    private ArrayList<ThuChi> listTC = new ArrayList<>();
    private  int layout;

    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");
    private DaoThuChi daoThuChi;
    private DatePickerDialog datePickerDialog;
    boolean isDark = false;

    public KhoanThuAdapter() {
    }

    public KhoanThuAdapter(Context context, ArrayList<GiaoDich> list, Boolean isDark) {
        this.context = context;
        this.list = list;
        this.isDark = isDark;
    }


    public KhoanThuAdapter(Context context, ArrayList<GiaoDich> list) {
        this.context = context;
        this.list = list;

    }
    public KhoanThuAdapter(Context context,int layout, ArrayList<GiaoDich> list) {
        this.context = context;
        this.list = list;
        this.layout=layout;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView img_avataitem;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textList);
            img_avataitem = itemView.findViewById(R.id.img_avataitem);
            relativeLayout = itemView.findViewById(R.id.relative_item);

        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(list.get(position).getMoTaGd());

        daoGiaoDich = new DaoGiaoDich(context);
        final GiaoDich gd = list.get(position);
        //Khi nhấn nút sửa
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        context, R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(context).inflate(
                        R.layout.bottom_sheet_khoahoc,
                        (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainer)
                );
                TextView txtXemchiTiet=bottomSheetView.findViewById(R.id.txt_XemChiTiet);
                TextView txtSuaKhoanChi=bottomSheetView.findViewById(R.id.txt_SuaThuChi);
                TextView txtXoa=bottomSheetView.findViewById(R.id.txt_XoaThuChi);
                txtSuaKhoanChi.setText("Sửa khoản thu");

                txtXemchiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        if (position == RecyclerView.NO_POSITION) return;
                        GiaoDich gd = list.get(position);
                        //Format dạng tiền
                        NumberFormat fm = new DecimalFormat("#,###");
                        //Hiện thông tin giao dịch khi click vào item
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.thong_tin_gd);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                        TextView mota, ngay, tien, loai, title;
                        mota = dialog.findViewById(R.id.mota_gd);
                        ngay = dialog.findViewById(R.id.ngay_gd);
                        tien = dialog.findViewById(R.id.tien_gd);
                        loai = dialog.findViewById(R.id.loai_gd);
                        title = dialog.findViewById(R.id.thongtinGD);
                        title.setText("THÔNG TIN THU");
                        mota.setText(gd.getMoTaGd());
                        ngay.setText(dfm.format(gd.getNgayGd()));
                        tien.setText(fm.format(gd.getSoTien()) + " VND");
                        daoThuChi = new DaoThuChi(context);
                        loai.setText(daoThuChi.getTen(gd.getMaKhoan()));

                        dialog.show();

                    }
                });
                txtSuaKhoanChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();

                        final Dialog dialog = new Dialog(context);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.them_khoan_thuchi);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                        Window window = dialog.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (dialog != null && dialog.getWindow() != null) {
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        }
                        final EditText moTaGd = dialog.findViewById(R.id.them_mota_gd);
                        final TextView ngayGd = dialog.findViewById(R.id.them_ngay_gd);
                        final EditText tienGd = dialog.findViewById(R.id.them_tien_gd);
                        final Spinner spLoaiGd = dialog.findViewById(R.id.spLoaiGd);
                        final TextView title = dialog.findViewById(R.id.titleThemKhoan);
                        final Button xoa = dialog.findViewById(R.id.xoaTextGD);
                        final Button them = dialog.findViewById(R.id.btnThemGD);
                        daoThuChi = new DaoThuChi(context);
                        listTC = daoThuChi.getThuChi(0);
                        //Set tiêu đề, text
                        title.setText("SỬA KHOẢN THU");
                        them.setText("SỬA");
                        moTaGd.setText(gd.getMoTaGd());
                        ngayGd.setText(String.valueOf(gd.getNgayGd()));
                        tienGd.setText(String.valueOf(gd.getSoTien()));
                        final ArrayAdapter sp = new ArrayAdapter(context, R.layout.spiner, listTC);
                        spLoaiGd.setAdapter(sp);
//               Toast.makeText(context, daoThuChi.getTen(gd.getMaKhoan()),Toast.LENGTH_SHORT).show();
                        int vitri = -1;
                        for (int i = 0; i < listTC.size(); i++) {
                            if (listTC.get(i).getTenKhoan().equalsIgnoreCase(daoThuChi.getTen(gd.getMaKhoan()))) {
                                vitri = i;
                                break;
                            }
                        }
                        spLoaiGd.setSelection(vitri);


                        //Khi nhấn ngày hiện lên lựa chọ ngày
                        ngayGd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Calendar calendar = Calendar.getInstance();
                                int d = calendar.get(Calendar.DAY_OF_MONTH);
                                int m = calendar.get(Calendar.MONTH);
                                int y = calendar.get(Calendar.YEAR);
                                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        final String NgayGD = dayOfMonth + "/" + month + "/" + year;
                                        ngayGd.setText(NgayGD);
                                    }
                                }, y, m, d);
                                datePickerDialog.show();
                            }
                        });


                        //Khi nhấn nút xóa
                        xoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        //Khi nhấn nút sửa
                        them.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String mota = moTaGd.getText().toString();
                                String ngay = ngayGd.getText().toString();
                                String tien = tienGd.getText().toString();
                                ThuChi tc = (ThuChi) spLoaiGd.getSelectedItem();
                                int ma = tc.getMaKhoan();
                                //Check lỗi
                                if (mota.isEmpty() && ngay.isEmpty() && tien.isEmpty()) {
                                    Toast.makeText(context, "Các trường không được để trống!", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        GiaoDich giaoDich = new GiaoDich(gd.getMaGd(), mota, dfm.parse(ngay), Integer.parseInt(tien), ma);
                                        if (daoGiaoDich.suaGD(giaoDich) == true) {
                                            list.clear();
                                            list.addAll(daoGiaoDich.getGDtheoTC(0));
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }

                            }
                        });

                        dialog.show();


                    }
                });

                txtXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        final Dialog dialog = new Dialog(context);

                        dialog.setContentView(R.layout.dialog_xoa);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                        Window window = dialog.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (dialog != null && dialog.getWindow() != null) {
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        }
                        final TextView txt_Massage = dialog.findViewById(R.id.txt_Titleconfirm);
                        Button xoa = dialog.findViewById(R.id.xoaTextLT);
                        final Button them = dialog.findViewById(R.id.btnThemLT);
                        final Button btn_Yes = dialog.findViewById(R.id.btn_yes);
                        final Button btn_No = dialog.findViewById(R.id.btn_no);
                        final ProgressBar progressBar = dialog.findViewById(R.id.progress_loadconfirm);
                        progressBar.setVisibility(View.INVISIBLE);
                        txt_Massage.setText("Bạn có muốn xóa " + list.get(position).getMoTaGd() + " hay không ? ");
                        btn_Yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (daoGiaoDich.xoaGD(gd) == true) {
                                    txt_Massage.setText("");
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            list.clear();
                                            list.addAll(daoGiaoDich.getGDtheoTC(0));
                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }, 2000);

                                }

                            }
                        });
                        btn_No.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        dialog.show();

                    }
                });
                bottomSheetView.findViewById(R.id.txt_Huy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });


        holder.img_avataitem.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}