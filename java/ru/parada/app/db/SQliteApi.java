package ru.parada.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import ru.parada.app.contracts.ImagesContract;
import ru.parada.app.contracts.PricesContract;
import ru.parada.app.contracts.ServicesWithPricesContract;
import ru.parada.app.core.ActionsCore;
import ru.parada.app.core.DoctorVideosCore;
import ru.parada.app.core.DoctorsCore;
import ru.parada.app.core.InfoCore;
import ru.parada.app.core.NewsCore;
import ru.parada.app.core.NotificationsCore;
import ru.parada.app.core.ServicesCore;
import ru.parada.app.core.ServicesWithPricesCore;
import ru.parada.app.modules.actions.model.Action;
import ru.parada.app.modules.actions.model.ActionsCursorListModel;
import ru.parada.app.modules.doctors.models.Doctor;
import ru.parada.app.modules.doctors.models.DoctorsCursorListModel;
import ru.parada.app.modules.doctors.videos.model.DoctorVideosCursorListModel;
import ru.parada.app.modules.doctors.videos.model.Video;
import ru.parada.app.modules.images.ImageModel;
import ru.parada.app.modules.info.model.Info;
import ru.parada.app.modules.info.model.InfoCursorListModel;
import ru.parada.app.modules.news.model.NewsCursorListModel;
import ru.parada.app.modules.news.model.OneOfNews;
import ru.parada.app.modules.notifications.model.Notification;
import ru.parada.app.modules.prices.models.PricesCursorListModel;
import ru.parada.app.modules.services.model.Service;
import ru.parada.app.modules.services.model.ServicesCursorListModel;
import ru.parada.app.modules.servicesprices.models.ServiceGroupPrice;
import ru.parada.app.modules.servicesprices.models.ServiceWithPrice;
import ru.parada.app.units.ArrayListModel;
import ru.parada.app.units.ListModel;

public class SQliteApi
{
    static private final String DB_NAME = "parada";
    static private final int DB_VERSION = 1611270230;
    static private volatile SQliteApi instanse;

    static public SQliteApi getInstanse()
    {
        if(instanse == null)
        {
            instanse = new SQliteApi();
        }
        return instanse;
    }

    private SQLiteDatabase sdb;
    private final DAO.Actions actions = new Tables.Actions()
    {
        @Override
        public ListModel<ActionsCore.Model> getAll()
        {
            return new ActionsCursorListModel(sdb.rawQuery("SELECT * " +
                    "FROM " + TABLE_NAME + " " +
                    "LEFT JOIN " + Tables.Images.TABLE_NAME + " " +
                    "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.ACTIONS_TYPE + " " +
                    "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id, new String[]{}));
        }
        @Override
        public ActionsCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.ACTION_DETAIL_TYPE + " "
                            + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + TABLE_NAME + "." + BaseColumns._ID + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            ActionsCore.Model model = new Action(id,
                    cursor.getString(cursor.getColumnIndex(Columns.descr)),
                    cursor.getString(cursor.getColumnIndex(Columns.title)),
                    cursor.getString(cursor.getColumnIndex(Columns.subtitle)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)),
                    cursor.getLong(cursor.getColumnIndex(Columns.from_date)),
                    cursor.getLong(cursor.getColumnIndex(Columns.to_date)));
            cursor.close();
            return model;
        }
        @Override
        public void insertOne(ActionsCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.News news = new Tables.News()
    {
        @Override
        public ListModel<NewsCore.Model> getAll()
        {
            return new NewsCursorListModel(sdb.rawQuery("SELECT * " + "FROM " + TABLE_NAME, new String[]{}));
        }
        @Override
        public ListModel<NewsCore.Model> getAllWithLimit(int limit)
        {
            return new NewsCursorListModel(sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "ORDER BY " + Columns.date + " DESC " + "LIMIT " + limit, new String[]{}));
        }
        @Override
        public NewsCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.ONEOFNEWS_TYPE + " "
                            + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + TABLE_NAME + "." + BaseColumns._ID + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            NewsCore.Model model = new OneOfNews(id,
                    cursor.getString(cursor.getColumnIndex(Columns.title)),
                    cursor.getString(cursor.getColumnIndex(Columns.descr)),
                    cursor.getString(cursor.getColumnIndex(Columns.full_descr)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)),
                    cursor.getLong(cursor.getColumnIndex(Columns.date)));
            cursor.close();
            return model;
        }
        @Override
        public void insertOne(NewsCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Services services = new Tables.Services()
    {
        @Override
        public ListModel<ServicesCore.Model> getAll()
        {
            return new ServicesCursorListModel(sdb.rawQuery("SELECT * " +
                    "FROM " + TABLE_NAME + " " +
                    "LEFT JOIN " + Tables.Images.TABLE_NAME + " " +
                    "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.SERVICES_TYPE + " " +
                    "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                            + "ORDER BY " + Columns.order + " ASC "
                    , null));
        }

        @Override
        public ServicesCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.SERVICE_DETAIL_TYPE + " "
                            + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + TABLE_NAME + "." + BaseColumns._ID + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            ServicesCore.Model model = new Service(id,
                    cursor.getInt(cursor.getColumnIndex(Columns.order)),
                    cursor.getString(cursor.getColumnIndex(Columns.title)),
                    cursor.getString(cursor.getColumnIndex(Columns.descr)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)));
            cursor.close();
            return model;
        }

        @Override
        public void insertOne(ServicesCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }

        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Doctors doctors = new Tables.Doctors()
    {
        @Override
        public ListModel<DoctorsCore.Model> getAll()
        {
            return new DoctorsCursorListModel(sdb.rawQuery(
                    "SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                    + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTORS_TYPE + " "
                    + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                            + "ORDER BY " + Columns.order + " ASC "
                    , null));
        }
        @Override
        public ListModel<DoctorsCore.Model> getFromKeys(String keys)
        {
            return new DoctorsCursorListModel(sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                    + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTORS_TYPE + " "
                    + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                    + "WHERE " + Columns.first_last_middle + " LIKE \"%" + keys.toLowerCase() + "%\"" + " " , new String[]{}));
        }
        @Override
        public DoctorsCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                    + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTORS_TYPE + " "
                    + "AND " + TABLE_NAME + "." + BaseColumns._ID + " = " + Tables.Images.Columns.entity_id + " "
                    + "WHERE " + TABLE_NAME + "." + BaseColumns._ID + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            DoctorsCore.Model doctor = new Doctor(id,
                    cursor.getString(cursor.getColumnIndex(Columns.last_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.first_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.middle_name)),
                    cursor.getString(cursor.getColumnIndex(Columns.first_position)),
                    cursor.getString(cursor.getColumnIndex(Columns.second_position)),
                    cursor.getString(cursor.getColumnIndex(Columns.third_position)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)),
                            cursor.getString(cursor.getColumnIndex(Columns.descr)),
                            cursor.getInt(cursor.getColumnIndex(Columns.order)),
                            cursor.getString(cursor.getColumnIndex(Columns.phone)));
            cursor.close();
            return doctor;
        }
        @Override
        public void insertOne(DoctorsCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Videos videos = new Tables.Videos()
    {
        @Override
        public ListModel<DoctorVideosCore.Model> getAllFromDoctorId(int id)
        {
//            Log.e(getClass().getName(), "getAllFromDoctorId " + id);
            return new DoctorVideosCursorListModel(sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTOR_VIDEOS_TYPE + " "
                            + "AND " + TABLE_NAME + "." + Columns.id + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + Columns.doctor_id + " = " + id + " "
                    , new String[]{}));
        }
        @Override
        public DoctorVideosCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.DOCTOR_VIDEOS_TYPE + " "
                            + "AND " + TABLE_NAME + "." + Columns.id + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + TABLE_NAME + "." + Columns.id + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            DoctorVideosCore.Model video = new Video(id,
                    cursor.getInt(cursor.getColumnIndex(Columns.doctor_id)),
                    cursor.getString(cursor.getColumnIndex(Columns.title)),
                    cursor.getString(cursor.getColumnIndex(Columns.descr)),
                    cursor.getString(cursor.getColumnIndex(Columns.link)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)));
            cursor.close();
            return video;
        }
        @Override
        public void insertOne(DoctorVideosCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final Tables.Images images = new Tables.Images()
    {
        @Override
        public ImagesContract.Model getOneFromTypeAndEntityId(final int type, final int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * " + "FROM " + TABLE_NAME + " " + "WHERE " + Columns.type + "=" + type + " " + "AND " + Columns.entity_id + "=" + id, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            ImagesContract.Model model = new ImageModel(id, type, cursor.getInt(cursor.getColumnIndex(Columns.entity_id)), cursor.getString(cursor.getColumnIndex(Columns.image_path)), cursor.getString(cursor.getColumnIndex(Columns.image_url)));
            cursor.close();
            return model;
        }

        @Override
        public String getUrl(int type, int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * " + "FROM " + TABLE_NAME + " " + "WHERE " + BaseColumns._ID + "=" + id + " " + "AND " + Columns.entity_id + "=" + type, new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            String image_url = cursor.getString(cursor.getColumnIndex(Columns.image_url));
            cursor.close();
            return image_url;
        }

        @Override
        public long insertOne(ImagesContract.Model item)
        {
            sdb.delete(TABLE_NAME, Columns.entity_id + "=" + item.getEntityId() + " " + "AND " + Columns.type + "=" + item.getType(), new String[]{});
            return sdb.insert(TABLE_NAME, null, ContentDriver.getContentValues(item));
        }
    };
    private final DAO.ServicesWithPrices servicesWithPrices = new Tables.ServicesWithPrices()
    {
        @Override
        public ListModel<ServicesWithPricesCore.Model> getAll()
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "ORDER BY " + Columns.group_order + " ASC, " + Columns.group_id + " ASC, " + Columns.order + " ASC ", null);
            ArrayList<ServicesWithPricesCore.Model> data = new ArrayList<>();
            if(cursor.moveToFirst())
            {
                do
                {
                    data.add(new ServiceWithPrice(
                            cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                            cursor.getString(cursor.getColumnIndex(Columns.title)),
                            cursor.getString(cursor.getColumnIndex(Columns.subtitle)),
                            cursor.getInt(cursor.getColumnIndex(Columns.order)),
                            cursor.getInt(cursor.getColumnIndex(Columns.group_id)),
                            cursor.getString(cursor.getColumnIndex(Columns.group)),
                            cursor.getInt(cursor.getColumnIndex(Columns.group_order))
                    ));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            return new ArrayListModel<>(data);
        }

        @Override
        public ServicesWithPricesCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + BaseColumns._ID + "=" + id, new String[]{});
            ServicesWithPricesCore.Model data = null;
            if(cursor.moveToFirst())
            {
                data = new ServiceWithPrice(
                        cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                        cursor.getString(cursor.getColumnIndex(Columns.title)),
                        cursor.getString(cursor.getColumnIndex(Columns.subtitle)),
                        cursor.getInt(cursor.getColumnIndex(Columns.order)),
                        cursor.getInt(cursor.getColumnIndex(Columns.group_id)),
                        cursor.getString(cursor.getColumnIndex(Columns.group)),
                        cursor.getInt(cursor.getColumnIndex(Columns.group_order))
                );
            }
            cursor.close();
            return data;
        }

        @Override
        public ListModel<ServicesWithPricesContract.GroupModel> getAllGroups()
        {
            Cursor cursor = sdb.rawQuery("SELECT DISTINCT "
                    + Columns.group + ", " + Columns.group_order + ", " + Columns.group_id + " "
                    + "FROM " + TABLE_NAME + " "
                    + "ORDER BY " + Columns.group_id + " ASC ", new String[]{});
            ArrayList<ServicesWithPricesContract.GroupModel> data = new ArrayList<>();
            if(cursor.moveToFirst())
            {
                do
                {
                    data.add(new ServiceGroupPrice(cursor.getInt(cursor.getColumnIndex(Columns.group_id)), cursor.getString(cursor.getColumnIndex(Columns.group)), cursor.getInt(cursor.getColumnIndex(Columns.group_order))));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            return new ArrayListModel<>(data);
        }

        @Override
        public ListModel<ServicesWithPricesCore.Model> getAllFromKeys(String keys)
        {
            Cursor cursor = sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + Columns.title_search + " LIKE \"%" + keys.toLowerCase() + "%\"" + " "
                    + "ORDER BY " + Columns.group_id + " ASC ", new String[]{});
            ArrayList<ServicesWithPricesCore.Model> data = new ArrayList<>();
            if(cursor.moveToFirst())
            {
                do
                {
                    data.add(new ServiceWithPrice(
                            cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                            cursor.getString(cursor.getColumnIndex(Columns.title)),
                            cursor.getString(cursor.getColumnIndex(Columns.subtitle)),
                            cursor.getInt(cursor.getColumnIndex(Columns.order)),
                            cursor.getInt(cursor.getColumnIndex(Columns.group_id)), cursor.getString(cursor.getColumnIndex(Columns.group)), cursor.getInt(cursor.getColumnIndex(Columns.group_order))));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            return new ArrayListModel<>(data);
        }

        @Override
        public ListModel<ServicesWithPricesContract.GroupModel> getGroupsFromKeys(String keys)
        {
            Cursor cursor = sdb.rawQuery("SELECT DISTINCT "
                    + Columns.group + ", " + Columns.group_order + ", " + Columns.group_id + " "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + Columns.title_search + " LIKE \"%" + keys.toLowerCase() + "%\"" + " "
                    + "ORDER BY " + Columns.group_id + " ASC" + " ", new String[]{});
            ArrayList<ServicesWithPricesContract.GroupModel> data = new ArrayList<>();
            if(cursor.moveToFirst())
            {
                do
                {
                    data.add(new ServiceGroupPrice(cursor.getInt(cursor.getColumnIndex(Columns.group_id)), cursor.getString(cursor.getColumnIndex(Columns.group)), cursor.getInt(cursor.getColumnIndex(Columns.group_order))));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            return new ArrayListModel<>(data);
        }

        @Override
        public void insertOne(ServicesWithPricesCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }

        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Prices prices = new Tables.Prices()
    {
        @Override
        public ListModel<PricesContract.Model> getAllFromServiceId(int id)
        {
            return new PricesCursorListModel(sdb.rawQuery("SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "WHERE " + Columns.service_id + "=" + id, new String[]{}));
        }
        @Override
        public void insertOne(PricesContract.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Notifications notifications = new Tables.Notifications()
    {
        @Override
        public ListModel<NotificationsCore.Model> getAll()
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                    + "FROM " + TABLE_NAME + " "
                    + "ORDER BY " + Columns.date + " DESC ", null);
            ArrayList<NotificationsCore.Model> data = new ArrayList<>();
            if(cursor.moveToFirst())
            {
                do
                {
                    data.add(new Notification(
                            cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                            cursor.getString(cursor.getColumnIndex(Columns.message)),
                            cursor.getLong(cursor.getColumnIndex(Columns.date)),
                            cursor.getInt(cursor.getColumnIndex(Columns.read)) == 1
                    ));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
            return new ArrayListModel<>(data);
        }
        @Override
        public void insertOne(NotificationsCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item, exist(item)), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public boolean exist(NotificationsCore.Model item)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "WHERE " + TABLE_NAME + "." + BaseColumns._ID + "=" + item.getId() + " "
                    , null);
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };
    private final DAO.Info info = new Tables.Info()
    {
        @Override
        public ListModel<InfoCore.Model> getAll()
        {
            return new InfoCursorListModel(sdb.rawQuery("SELECT * " + "FROM " + TABLE_NAME, null));
        }
        @Override
        public void insertOne(InfoCore.Model item)
        {
            sdb.insertWithOnConflict(TABLE_NAME, null, ContentDriver.getContentValues(item), SQLiteDatabase.CONFLICT_REPLACE);
        }
        @Override
        public InfoCore.Model getOneFromId(int id)
        {
            Cursor cursor = sdb.rawQuery(
                    "SELECT * "
                            + "FROM " + TABLE_NAME + " "
                            + "LEFT JOIN " + Tables.Images.TABLE_NAME + " "
                            + "ON " + Tables.Images.Columns.type + " = " + ImagesContract.Types.INFO_TYPE + " "
                            + "AND " + TABLE_NAME + "." + Columns.id + " = " + Tables.Images.Columns.entity_id + " "
                            + "WHERE " + TABLE_NAME + "." + Columns.id + "=" + id + " "
                    , new String[]{});
            if(!cursor.moveToFirst())
            {
                cursor.close();
                return null;
            }
            InfoCore.Model model = new Info(id,
                    cursor.getString(cursor.getColumnIndex(Columns.title)),
                    cursor.getString(cursor.getColumnIndex(Columns.descr)),
                    cursor.getString(cursor.getColumnIndex(Tables.Images.Columns.image_path)));
            cursor.close();
            return model;
        }
        @Override
        public void clear()
        {
            sdb.execSQL("drop table if exists " + TABLE_NAME);
            sdb.execSQL(CREATE_TABLE);
        }
    };

    private SQliteApi()
    {

    }

    public void createDB(Context context)
    {
        sdb = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)
        {
            @Override
            public void onCreate(SQLiteDatabase db)
            {
                createTables(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
                clearTables(db);
                onCreate(db);
            }
        }.getWritableDatabase();
    }

    public void startTransaction()
    {
        sdb.beginTransaction();
    }

    public void endTransaction()
    {
        sdb.setTransactionSuccessful();
        sdb.endTransaction();
    }

    public DAO.News getNews()
    {
        return news;
    }
    public DAO.Actions getActions()
    {
        return actions;
    }
    public DAO.Services getServices()
    {
        return services;
    }
    public DAO.Doctors getDoctors()
    {
        return doctors;
    }
    public Tables.Images getImages()
    {
        return images;
    }
    public DAO.Prices getPrices()
    {
        return prices;
    }
    public DAO.ServicesWithPrices getServicesWithPrices()
    {
        return servicesWithPrices;
    }
    public DAO.Notifications getNotifications()
    {
        return notifications;
    }
    public DAO.Videos getVideos()
    {
        return videos;
    }
    public DAO.Info getInfo()
    {
        return info;
    }

    private void clearTables(SQLiteDatabase db)
    {
        db.execSQL("drop table if exists " + Tables.News.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Services.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Doctors.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Images.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.ServicesWithPrices.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Prices.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Actions.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Notifications.TABLE_NAME);
        db.execSQL("drop table if exists " + Tables.Info.TABLE_NAME);
    }

    private void createTables(SQLiteDatabase db)
    {
        db.execSQL(Tables.News.CREATE_TABLE);
        db.execSQL(Tables.Services.CREATE_TABLE);
        db.execSQL(Tables.Doctors.CREATE_TABLE);
        db.execSQL(Tables.Images.CREATE_TABLE);
        db.execSQL(Tables.ServicesWithPrices.CREATE_TABLE);
        db.execSQL(Tables.Prices.CREATE_TABLE);
        db.execSQL(Tables.Actions.CREATE_TABLE);
        db.execSQL(Tables.Notifications.CREATE_TABLE);
        db.execSQL(Tables.Info.CREATE_TABLE);
    }
}