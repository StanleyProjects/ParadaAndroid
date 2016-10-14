package ru.parada.app.contracts;

public interface DoctorDetailContract
{
    interface Mark
    {
        String DOCTOR_ID = Mark.class.getCanonicalName().toLowerCase().replace(".", "_") + "_" + "doctor_id";
    }

    interface Model
    {
        int getId();
        String getPhotoPath();
        String getLastName();
        String getFirstName();
        String getMiddleName();
        String getFirstPosition();
        String getSecondPosition();
        String getThirdPosition();
        String getDescription();
        String getPhone();
        int getOrder();
    }

    interface View
    {
        void update(Model data);
    }

    interface Presenter
    {
        void update(int id);
    }

    interface Behaviour
    {
        void back();
    }
}