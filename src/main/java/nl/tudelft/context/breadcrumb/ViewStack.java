package nl.tudelft.context.breadcrumb;

import nl.tudelft.context.controller.ViewController;

import java.util.Observable;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 14-5-2015
 */
public final class ViewStack extends Observable {

    /**
     * Stack containing views.
     */
    Stack<ViewController> viewStack = new Stack<>();

    /**
     * Clear all items from the stack.
     */
    public void clear() {
        viewStack.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Remove top item from the stack.
     *
     * @return Removed item
     */
    public ViewController pop() {
        ViewController viewController = viewStack.pop();
        setChanged();
        notifyObservers();
        return viewController;
    }

    /**
     * Look at the item on top of the stack.
     *
     * @return Item on top of the stack
     */
    public ViewController peek() {
        return viewStack.peek();
    }

    /**
     * Add a new item on the stack.
     *
     * @param viewController Item to add.
     * @return If stack is changed.
     */
    public boolean add(final ViewController viewController) {
        boolean changed = viewStack.add(viewController);
        setChanged();
        notifyObservers();
        return changed;
    }

    /**
     * Return the number of components of the view stack.
     *
     * @return Number of components of the view stack
     */
    public int size() {
        return viewStack.size();
    }

    /**
     * Returns a sequential stream with this collection as its source.
     *
     * @return a sequential stream over the elements in this collection
     */
    public Stream<ViewController> stream() {
        return viewStack.stream();
    }

}
