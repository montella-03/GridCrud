package com.example.store.ui;

import com.example.store.Backend.products.Product;
import com.example.store.Backend.products.ProductService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.reports.PrintPreviewReport;

@Route("report")
@RolesAllowed("MANAGER")
public class ReportView extends VerticalLayout {

        public ReportView(ProductService productService) {
            var report= new PrintPreviewReport<>(Product.class,"name","description","farm","category","quantity");
            report.getReportBuilder().setTitle("Products Report");
            report.setItems(productService.findAll());

            StreamResource pdf = report.getStreamResource("products.pdf", productService::findAll, PrintPreviewReport.Format.PDF);
            StreamResource csv = report.getStreamResource("products.csv", productService::findAll, PrintPreviewReport.Format.CSV);
            add(
                    new HorizontalLayout(
                            new Anchor(pdf, "Download PDF"),
                            new Anchor(csv, "Download csv")
                    ),
                report
            );
        }
}
