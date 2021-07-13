package com.abdalazizabdallah.tawseelat.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentSearchFragmentBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;


public class SearchFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SearchFragment";
    private FragmentSearchFragmentBinding fragmentSearchFragmentBinding;
    private NavController navController;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchFragmentBinding = FragmentSearchFragmentBinding.inflate(inflater, container, false);
        return fragmentSearchFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        configurationSearchView();
        fragmentSearchFragmentBinding.retryButton.setOnClickListener(this);
        if (!PublicHelper.isConnectionInternetSuccessfully(requireContext())) {
            fragmentSearchFragmentBinding.linearGroup.setVisibility(View.VISIBLE);
            fragmentSearchFragmentBinding.recycleView.setVisibility(View.GONE);
        } else {
            //TODO : make Query
        }

    }

    private void configurationSearchView() {
        MenuItem searchMenuItem = fragmentSearchFragmentBinding.toolbar.getMenu().findItem(R.id.search_item);
        searchMenuItem.expandActionView();

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                System.out.println("Expand");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                System.out.println("Collapse");
                //finish();
                navController.popBackStack();
                return true;
            }
        });

        //------------------set configuration in search  view to show icon voice search
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));

        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(ResourcesCompat.getColor(requireActivity().getResources()
                , R.color.my_purple_Dark, null));
        searchEditText.setHintTextColor(ResourcesCompat.getColor(requireActivity().getResources()
                , R.color.my_purple_Dark, null));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            searchEditText.setBackgroundTintList(ColorStateList.valueOf(
//                    ResourcesCompat.getColor(requireActivity().getResources()
//                            ,R.color.my_purple_Dark,null)
//            ));
//        }


        ImageView imageViewClose = searchView.findViewById(R.id.search_close_btn);
//        ImageView imageViewIconSearch = searchView.findViewById(R.id.search_mag_icon);
        ImageView imageViewVoice = searchView.findViewById(R.id.search_voice_btn);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewClose.setImageTintList(ColorStateList.valueOf(ResourcesCompat.getColor(requireActivity().getResources()
                    , R.color.my_purple_Dark, null)));
//            imageViewIcon.setImageTintList(ColorStateList.valueOf(ResourcesCompat.getColor(requireActivity().getResources()
//                    ,R.color.my_purple_Dark,null)));
            imageViewVoice.setImageTintList(ColorStateList.valueOf(ResourcesCompat.getColor(requireActivity().getResources()
                    , R.color.my_purple_Dark, null)));
        }


        //--------------Query by search view-------------------
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO : TO MAKE SEARCH ABOUT SPECIFIC COMPANY
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void search_handle(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra(SearchManager.QUERY);
            EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            editText.setText(stringExtra);
            editText.setSelection(stringExtra.length());
            //TODO : TO MAKE SEARCH ABOUT SPECIFIC COMPANY
        }
    }

    @Override
    public void onClick(View v) {
        //TODO : retry event
    }
}