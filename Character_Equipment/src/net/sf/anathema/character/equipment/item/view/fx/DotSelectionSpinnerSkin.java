package net.sf.anathema.character.equipment.item.view.fx;

import com.sun.javafx.Utils;
import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.ListSpinnerBehavior;
import jfxtras.labs.scene.control.ListSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A heavily modified version of Jonathan Giles's RatingSkin from the ControlsFX project
 */
@SuppressWarnings("UnusedDeclaration")
public class DotSelectionSpinnerSkin<T> extends SkinBase<ListSpinner<T>, ListSpinnerBehavior<T>> {

  private static final String FILLED = "filled";
  private static final String EMPTY = "empty";
  private static final String RATING_PROPERTY = "RATING";
  private static final String MAXIMUM_PROPERTY = "MAX";

  private Pane container;
  private double rating = -1;

  private final EventHandler<MouseEvent> mouseClickHandler = new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
      Point2D location = new Point2D(event.getSceneX(), event.getSceneY());
      updateRating(Double.valueOf(calculateRating(location)).intValue());
    }
  };

  public DotSelectionSpinnerSkin(ListSpinner<T> control) {
    super(control, new ListSpinnerBehavior<>(control));
    getStyleClass().add("invisiblecontainer");
    createButtons();
    updateRating((Integer) getSkinnable().getValue());
    registerChangeListener(control.valueProperty(), RATING_PROPERTY);
  }


  @Override
  protected void handleControlPropertyChanged(String p) {
    super.handleControlPropertyChanged(p);
    if (p.equals(RATING_PROPERTY)) {
      updateRating();
    }
  }

  private void createButtons() {
    container = new HBox();
    container.setOnMouseClicked(mouseClickHandler);
    container.setOnMouseDragExited(mouseClickHandler);
    for (int index = 0; index < getMaximumValue(); index++) {
      Node backgroundNode = createButton();
      container.getChildren().add(backgroundNode);
    }
    getChildren().setAll(container);
  }

  private double calculateRating(Point2D location) {
    Point2D pointInElement = container.sceneToLocal(location);
    double x = pointInElement.getX();
    int max = getMaximumValue();
    double width = getSkinnable().getWidth();
    double newRating = (x / width) * max;
    if (newRating < 0.5) {
      newRating = 0;
    } else {
      newRating = Utils.clamp(1, Math.ceil(newRating), getMaximumValue());
    }
    return newRating;
  }

  /**Drawing code adapted from JFXtras SimpleIndicatorSkin.*/
  private Node createButton() {
    double size = 20;
    Group indicator = new Group();
    indicator.getStyleClass().add(FILLED);
    indicator.getChildren().clear();
    Shape outerBounds = new Rectangle(0, 0, size, size);
    outerBounds.setOpacity(0.0);
    indicator.getChildren().add(outerBounds);
    Circle frame = new Circle(0.5 * size, 0.5 * size, 0.4 * size);
    frame.getStyleClass().add("indicator-inner-frame-fill");

    Circle corpus = new Circle(0.5 * size, 0.5 * size, 0.38 * size);
    corpus.getStyleClass().add("indicator-main-fill");

    InnerShadow innerShadow = new InnerShadow();
    innerShadow.setWidth(0.2880 * corpus.getLayoutBounds().getWidth());
    innerShadow.setHeight(0.2880 * corpus.getLayoutBounds().getHeight());
    innerShadow.setOffsetX(0.0);
    innerShadow.setOffsetY(0.0);
    innerShadow.setRadius(0.2880 * corpus.getLayoutBounds().getWidth());
    innerShadow.setColor(Color.BLACK);
    innerShadow.setBlurType(BlurType.GAUSSIAN);
    corpus.setEffect(innerShadow);

    Ellipse highlight = new Ellipse(0.504 * size, 0.294 * size, 0.26 * size, 0.15 * size);
    highlight.getStyleClass().add("indicator-highlight-fill");

    indicator.getChildren().addAll(frame, corpus, highlight);
    indicator.setCache(true);
    return indicator;
  }

  private void updateRating() {
    updateRating(getSkinnable().getIndex());
  }

  private void updateRating(Integer newRating) {
    if (newRating == rating) {
      return;
    }
    rating = Utils.clamp(0, newRating, getMaximumValue());
    if (!getSkinnable().valueProperty().isBound()) {
      getSkinnable().setValue((T) (Integer) Double.valueOf(rating).intValue());
    }
    int max = getMaximumValue();
    List<Node> buttons = new ArrayList<>(container.getChildren());
    for (int index = 0; index < max; index++) {
      Node button = buttons.get(index);
      List<String> styleClass = button.getStyleClass();
      boolean isFilled = styleClass.contains(FILLED);
      if (index < rating) {
        if (!isFilled) {
          styleClass.remove(EMPTY);
          styleClass.add(FILLED);
        }
      } else if (isFilled) {
        styleClass.remove(FILLED);
        styleClass.add(EMPTY);
      }
    }
  }

  private int getMaximumValue() {
    ObservableList<Integer> items = (ObservableList<Integer>) getSkinnable().getItems();
    return Collections.max(items);
  }
}