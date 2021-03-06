package com.hhuebner.autogp.core.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hhuebner.autogp.core.InputHandler;
import com.hhuebner.autogp.core.component.furniture.FurnitureItem;
import com.hhuebner.autogp.core.engine.BoundingBox;
import com.hhuebner.autogp.core.util.Direction;
import com.hhuebner.autogp.core.util.Utility;
import com.hhuebner.autogp.options.OptionsHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static com.hhuebner.autogp.core.engine.GPEngine.CELL_SIZE;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class FurnitureComponent extends InteractableComponent {

    private final FurnitureItem item;
    private final Direction facing;

    public FurnitureComponent(@JsonProperty("bb") BoundingBox bb, @JsonProperty("facing") Direction facing,
                              @JsonProperty("item") FurnitureItem item, @JsonProperty("id") long id) {
        super(bb, item.getName(), id);
        this.item = item;
        this.facing = facing;
    }

    @Override
    public void render(GraphicsContext ctx, InputHandler inputHandler) {
        ctx.save();

        double scaledX = bb.x * CELL_SIZE;
        double scaledY = bb.y* CELL_SIZE;
        double scaledW = bb.getWidth() * CELL_SIZE;
        double scaledH = bb.getHeight() * CELL_SIZE;
        double scaledItemW = item.getWidth() * CELL_SIZE;
        double scaledItemH = item.getHeight() * CELL_SIZE;

        if (OptionsHandler.INSTANCE.DEBUG) {
            ctx.setStroke(Color.RED);
            ctx.strokeRect(scaledX, scaledY, scaledW, scaledH);
            ctx.setStroke(Color.BLACK);
        }

        if (this.facing == Direction.EAST)
            scaledX += scaledItemH;

        if (this.facing == Direction.WEST) {
            scaledY += scaledItemW;
        }

        if (this.facing == Direction.SOUTH) {
            scaledX += scaledItemW;
            scaledY += scaledItemH;
        }

        ctx.translate(scaledX, scaledY);
        ctx.rotate(facing.angle);

        this.item.getRenderer().render(ctx, item, 0, 0, scaledItemW, scaledItemH);
        //ctx.drawImage(this.item.getImage(), 0, 0, scaledW, scaledH);
        ctx.restore();
    }

    public FurnitureItem getItem() {
        return this.item;
    }

    public Direction getFacing() {
        return facing;
    }
}
