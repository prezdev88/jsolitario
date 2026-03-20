package org.netbeans.lib.awtextra;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AbsoluteLayout implements LayoutManager2, Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Component, AbsoluteConstraints> constraints = new HashMap<>();

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // Legacy LayoutManager API. Components added this way keep their current bounds.
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return computeLayoutSize(parent, SizeType.PREFERRED);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return computeLayoutSize(parent, SizeType.MINIMUM);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            for (Component component : parent.getComponents()) {
                if (!component.isVisible()) {
                    continue;
                }

                AbsoluteConstraints absoluteConstraints = constraints.get(component);
                if (absoluteConstraints == null) {
                    continue;
                }

                Dimension size = component.getPreferredSize();
                int width = absoluteConstraints.width >= 0 ? absoluteConstraints.width : size.width;
                int height = absoluteConstraints.height >= 0 ? absoluteConstraints.height : size.height;
                component.setBounds(absoluteConstraints.x, absoluteConstraints.y, width, height);
            }
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraint) {
        if (constraint == null) {
            constraints.remove(comp);
            return;
        }
        if (!(constraint instanceof AbsoluteConstraints absoluteConstraints)) {
            throw new IllegalArgumentException("Constraint must be an AbsoluteConstraints instance");
        }
        constraints.put(comp, absoluteConstraints);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.0f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.0f;
    }

    @Override
    public void invalidateLayout(Container target) {
        // No cached state.
    }

    private Dimension computeLayoutSize(Container parent, SizeType sizeType) {
        synchronized (parent.getTreeLock()) {
            int width = 0;
            int height = 0;

            for (Component component : parent.getComponents()) {
                if (!component.isVisible()) {
                    continue;
                }

                AbsoluteConstraints absoluteConstraints = constraints.get(component);
                Dimension size = sizeType == SizeType.MINIMUM
                    ? component.getMinimumSize()
                    : component.getPreferredSize();

                if (absoluteConstraints != null) {
                    int componentWidth = absoluteConstraints.width >= 0 ? absoluteConstraints.width : size.width;
                    int componentHeight = absoluteConstraints.height >= 0 ? absoluteConstraints.height : size.height;
                    width = Math.max(width, absoluteConstraints.x + componentWidth);
                    height = Math.max(height, absoluteConstraints.y + componentHeight);
                } else {
                    width = Math.max(width, component.getX() + size.width);
                    height = Math.max(height, component.getY() + size.height);
                }
            }

            return new Dimension(width, height);
        }
    }

    private enum SizeType {
        MINIMUM,
        PREFERRED
    }
}
