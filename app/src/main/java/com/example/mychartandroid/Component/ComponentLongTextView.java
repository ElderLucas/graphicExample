package com.example.mychartandroid.Component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mychartandroid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.component_long_text_view)
public class ComponentLongTextView extends LinearLayout {

    @ViewById
    TextView textViewLongText;

    public ComponentLongTextView(Context context) {
        super(context);
    }

    public ComponentLongTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void initView() {
        // Configurações iniciais do textViewLongText

        // Este método é chamado após a injeção das views, garantindo que textViewLongText não seja nulo
        textViewLongText.setMaxLines(3);
        textViewLongText.setEllipsize(TextUtils.TruncateAt.END);

        // Configure o click listener aqui se for comum a todas as instâncias ou depende de inicializações
        this.setOnClickListener(v -> showFullTextPopup(textViewLongText.getText().toString()));
    }

    // Método para configurar o texto
    public void setText(String text) {
        textViewLongText.setText(text);
    }

    private void showFullTextPopup(String text) {
        Context context = getContext(); // Certifique-se de usar getContext() apropriado

        // Infla o layout personalizado que já inclui o botão
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popup_content_layout, null);

        // Configura o texto
        TextView textView = popupView.findViewById(R.id.textViewPopupContent);
        textView.setText(text);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(popupView);

        // Aqui, criamos o AlertDialog, mas não chamamos show() ainda
        final AlertDialog dialog = builder.create();

        // Configura a ação do botão
        Button customButton = popupView.findViewById(R.id.customButton);
        customButton.setOnClickListener(v -> {
            // Ação para fechar o diálogo
            dialog.dismiss();
        });

        // Mostra o AlertDialog depois de configurar tudo, incluindo o listener do botão
        dialog.show();
    }


}
