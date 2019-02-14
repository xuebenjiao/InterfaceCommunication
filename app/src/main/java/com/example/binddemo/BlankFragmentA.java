package com.example.binddemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.interfacefunction.FunctionManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BlankFragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentA extends Fragment implements View.OnClickListener{
    private FunctionManager functionManager;
    private Button btnWithNoAll,btnWithParamOnly,btnWithResultOnly,btnWithAll;
    public static final String FUNCTION_NAME = BlankFragmentA.class.getName()+"WithAll";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentA newInstance(String param1, String param2) {
        BlankFragmentA fragment = new BlankFragmentA();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_blank, container, false);
        btnWithNoAll = view.findViewById(R.id.no_all_btn);
        btnWithParamOnly = view.findViewById(R.id.with_param_only_btn);
        btnWithResultOnly = view.findViewById(R.id.with_result_only_btn);
        btnWithAll = view.findViewById(R.id.with_all_btn);
        btnWithNoAll.setOnClickListener(this);
        btnWithParamOnly.setOnClickListener(this);
        btnWithResultOnly.setOnClickListener(this);
        btnWithAll.setOnClickListener(this);
        functionManager = FunctionManager.getInstance();
        return view ;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.no_all_btn:
                functionManager.invokeNoAll(BlankFragmentA.FUNCTION_NAME);
                break;
            case  R.id.with_param_only_btn:
                functionManager.invokeWithParamOnly(BlankFragmentA.FUNCTION_NAME,"有参无返回值");
                break;
            case  R.id.with_result_only_btn:
                String result =      functionManager.invokeWithResultOnly(BlankFragmentA.FUNCTION_NAME,String.class);
                Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
                break;
            case  R.id.with_all_btn:
                String str =  functionManager.invokeWithAll(BlankFragmentA.FUNCTION_NAME,String.class,"有参有返回值");
                Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }

    }


}
