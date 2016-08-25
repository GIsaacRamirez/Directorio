package com.example.isaac.directorioudg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;

import java.io.File;

public class pdfView extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        String directory = "/sdcard/";

        File file = new File(directory, "sample.pdf");

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
        scrollBar.setHorizontal(true);
        pdfView.setScrollBar(scrollBar);


        pdfView.fromFile(file)
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeVertical(false)
                .defaultPage(1)
                .showPageWithAnimation(true)
                .load();

        setTitle("Gaceta");
    }


}
