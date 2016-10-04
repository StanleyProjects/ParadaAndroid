package ru.parada.app.modules.images;

import ru.parada.app.contracts.ImagesContract;

public class ImageModel
        implements ImagesContract.Model
{
    private int id;
    private int type;
    private int entity_id;
    private String image_path;
    private String image_url;

    public ImageModel(int i, int t, int e, String ip, String iu)
    {
        this.id = i;
        this.type = t;
        this.entity_id = e;
        this.image_path = ip;
        this.image_url = iu;
    }
    public ImageModel(int t, int e, String ip, String iu)
    {
        this(0, t, e, ip, iu);
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public int getType()
    {
        return type;
    }

    @Override
    public int getEntityId()
    {
        return entity_id;
    }

    @Override
    public String getImagePath()
    {
        return image_path;
    }

    @Override
    public String getImageUrl()
    {
        return image_url;
    }
}