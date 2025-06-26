package com.rm.lavarapidorm.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

public class ZoomUtils {

    private static final Map<Component, Font> originalFonts = new HashMap<>();
    private static final Map<Component, Dimension> originalSizes = new HashMap<>();
    private static final Map<Component, Point> originalLocations = new HashMap<>();

    /**
     * Armazena as propriedades originais (fontes, tamanhos, posições) de um componente e seus filhos.
     */
    public static void storeOriginalProperties(Component component) {
        // Armazenar fonte
        if (component.getFont() != null) {
            originalFonts.putIfAbsent(component, component.getFont());
        }

        // Armazenar tamanho
        if (component.getSize() != null) {
            originalSizes.putIfAbsent(component, component.getSize());
        }

        // Armazenar posição para layouts absolutos
        if (component.getParent() != null && component.getParent().getLayout() == null) {
            if (component.getLocation() != null) {
                originalLocations.putIfAbsent(component, component.getLocation());
            }
        }

        // Armazenar propriedades dos filhos
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                storeOriginalProperties(child);
            }
        }
    }

    /**
     * Reseta todas as propriedades armazenadas.
     */
    public static void resetStoredProperties() {
        originalFonts.clear();
        originalSizes.clear();
        originalLocations.clear();
    }

    /**
     * Atualiza o tamanho e a fonte de um componente e seus filhos com base em uma escala.
     */
public static void updateComponentSize(Component component, double scale) {
    // Usar a fonte original para calcular o tamanho da fonte
    Font originalFont = originalFonts.get(component);
    if (originalFont != null) {
        float newSize = (float) (originalFont.getSize() * scale);
        component.setFont(originalFont.deriveFont(newSize));
    }

    // Usar o tamanho original para calcular o novo tamanho
    Dimension originalSize = originalSizes.get(component);
    if (originalSize != null) {
        int newWidth = (int) (originalSize.width * scale);
        int newHeight = (int) (originalSize.height * scale);

        // Atualizar o tamanho do componente
        component.setSize(newWidth, newHeight);

        // Atualizar o tamanho preferido, mas apenas se necessário
        if (!(component instanceof JScrollPane) && !(component instanceof JTable)) {
            component.setPreferredSize(new Dimension(newWidth, newHeight));
        }
    }

    // Usar a posição original para calcular a nova posição (para layouts absolutos)
    Point originalLocation = originalLocations.get(component);
    if (originalLocation != null) {
        int newX = (int) (originalLocation.x * scale);
        int newY = (int) (originalLocation.y * scale);
        component.setLocation(newX, newY);
    }

    // Ajustar JTable
    if (component instanceof JTable) {
        JTable table = (JTable) component;

        // Ajustar a altura das linhas
        Dimension rowHeightDimension = originalSizes.get(table);
        if (rowHeightDimension != null) {
            table.setRowHeight((int) (rowHeightDimension.height * scale));
        }

        // Ajustar fonte do cabeçalho
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            Font headerFont = originalFonts.get(header);
            if (headerFont != null) {
                float newSize = (float) (headerFont.getSize() * scale);
                header.setFont(headerFont.deriveFont(newSize));
            }
        }
    }

    // Ajustar JScrollPane
    if (component instanceof JScrollPane) {
        JScrollPane scrollPane = (JScrollPane) component;

        // Redimensionar viewport
        Component viewportView = scrollPane.getViewport().getView();
        if (viewportView != null) {
            updateComponentSize(viewportView, scale);
        }
    }

    // Recursivamente aplicar zoom aos filhos
    if (component instanceof Container) {
        for (Component child : ((Container) component).getComponents()) {
            updateComponentSize(child, scale);
        }
    }

    // Atualizar layout, se necessário
    component.revalidate();
    component.repaint();
 }
}


