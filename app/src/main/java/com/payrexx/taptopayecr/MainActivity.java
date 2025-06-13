package com.payrexx.taptopayecr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.payrexx.taptopayecr.dto.ActionDto;
import com.payrexx.taptopayecr.dto.Dto;
import com.payrexx.taptopayecr.dto.SaleDataDTO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button submitButton = findViewById(R.id.Start);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountInput = findViewById(R.id.Amount);
                EditText amountTipInput = findViewById(R.id.TipAmount);
                EditText orderReferenceInput = findViewById(R.id.OrderReference);

                SaleDataDTO dataDto = new SaleDataDTO();
                try {
                    dataDto.amount = Integer.parseInt(amountInput.getText().toString());
                } catch (NumberFormatException e) {
                    dataDto.amount = 0;
                }
                try {
                    dataDto.tip = Integer.parseInt(amountTipInput.getText().toString());
                } catch (NumberFormatException e) {
                    dataDto.tip = 0;
                }
                dataDto.order_reference = orderReferenceInput.getText().toString();

                ActionDto actionDto = new ActionDto();
                actionDto.operation = "sale";
                actionDto.data = dataDto;

                Dto dto = new Dto();
                dto.payload = actionDto;

                Gson gson = new Gson();
                String json = gson.toJson(dto);
                Log.d("Payrexx Tap2Pay ECR", json);

                Intent intent = new Intent("com.payrexx.taptopay.SOFTPOS");
                intent.putExtra("com.payrexx.taptopay.CONFIGURATION", json);
                startActivity(intent);
            }
        });
    }
}