package edu.gatech.cs2340.team.imperialtrader.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;

import edu.gatech.cs2340.team.imperialtrader.R;
import edu.gatech.cs2340.team.imperialtrader.entity.Player;
import edu.gatech.cs2340.team.imperialtrader.viewmodels.ConfigurationViewModel;
import edu.gatech.cs2340.team.imperialtrader.viewmodels.RegionViewModel;

/**
 * Fragment to create a player
 */

public class CreatePlayerFrag extends Fragment {

    private CreatePlayerClickListener cpClickListener;

    @Override
    /**
     * Method for onAttach
     * @param context Context
     */
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            cpClickListener = (CreatePlayerClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnHeadlineSelectedListener");
        }
    }

    /**
     * reference to our view model
     */
    private ConfigurationViewModel ConfigViewModel;
    private RegionViewModel regionViewModel;


    /* ************************
        Widgets we will need for binding and getting information
     */
    private EditText nameField;
    private EditText pilotField;
    private EditText fighterField;
    private EditText traderField;
    private EditText engineerField;
    private Spinner difficultySpinner;
    private TextView errorText;
    private TextView errorNumText;
    private TextView successfulText;

    /* ***********************
       Data for player being edited.
     */
    private Player player;

    /* ***********************
       flag for whether this is a new player being created or an existing player being edited;
     */
    //private boolean editing;


    @Nullable
    @Override
    /**
     * Method for onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.create_player,
                container, false);
        /*
         * Grab the dialog widgets so we can get info for later
         */
        nameField = view.findViewById(R.id.playerName);
        pilotField = view.findViewById(R.id.pilotPoints);
        fighterField = view.findViewById(R.id.fighterPoints);
        traderField = view.findViewById(R.id.traderPoints);
        engineerField = view.findViewById(R.id.engineerPoints);
        difficultySpinner = view.findViewById(R.id.difficulty_spinner);
        errorText = view.findViewById(R.id.pointError);
        errorNumText = view.findViewById(R.id.numError);
        successfulText = view.findViewById(R.id.successfulIndicator);
        Button button = view.findViewById(R.id.createPlayer);

        List<String> dif = new ArrayList<>();
        dif.add("easy");
        dif.add("normal");
        dif.add("hard");
        dif.add("suicidal");

        /*
          Set up the adapter to display the allowable difficulty in the spinner
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, dif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);

        ConfigViewModel = ViewModelProviders.of(this).get(ConfigurationViewModel.class);
        regionViewModel = ViewModelProviders.of(this).get(RegionViewModel.class);
        player = new Player("default");

        button.setOnClickListener(v -> {
            Log.d("Create", "Create Player Pressed");
            player.setName(nameField.getText().toString());
            if ("".equals(pilotField.getText().toString()) 
                || "".equals(fighterField.getText().toString()) 
                || "".equals(traderField.getText().toString()) 
                || "".equals(engineerField.getText().toString())) {
                errorNumText.setVisibility(View.VISIBLE);
                successfulText.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.INVISIBLE);
            } else {
                errorNumText.setVisibility(View.INVISIBLE);
                player.setPilotPoints(Integer.parseInt(pilotField.getText().toString()));
                player.setFighterPoints(Integer.parseInt(fighterField.getText().toString()));
                player.setTraderPoints(Integer.parseInt(traderField.getText().toString()));
                player.setEngineerPoints(Integer.parseInt(engineerField.getText().toString()));
                player.setDifficulty((String) difficultySpinner.getSelectedItem());
                player.setCurRegion(regionViewModel.getHomeRegion(), -1);

                final int totalPoints = 16;

                if (player.getTotalPoints() != totalPoints) {
                    errorText.setVisibility(View.VISIBLE);
                    successfulText.setVisibility(View.INVISIBLE);
                } else {
                    final int delay = 2000;
                    Log.d("Edit", "Got new player data: " + player);
                    errorText.setVisibility(View.INVISIBLE);
                    successfulText.setVisibility(View.VISIBLE);
                    ConfigViewModel.createPlayer(player);
                    new android.os.Handler().postDelayed(
                            () -> cpClickListener.onCreateClick(),
                            delay);
                }
            }
        });
        return view;
    }
}
