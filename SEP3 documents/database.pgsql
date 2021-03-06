PGDMP     -                    v            postgres    9.6.2    10.1 R    6	           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            7	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            	            2615    24751    freshfitness    SCHEMA        CREATE SCHEMA freshfitness;
    DROP SCHEMA freshfitness;
             postgres    false            �           1247    24755    dateofbirth    DOMAIN     #   CREATE DOMAIN dateofbirth AS date;
 &   DROP DOMAIN freshfitness.dateofbirth;
       freshfitness       postgres    false    9            �           1247    24756    email    DOMAIN     .   CREATE DOMAIN email AS character varying(50);
     DROP DOMAIN freshfitness.email;
       freshfitness       postgres    false    9            �           1247    24761    endtime    DOMAIN     1   CREATE DOMAIN endtime AS time without time zone;
 "   DROP DOMAIN freshfitness.endtime;
       freshfitness       postgres    false    9            �           1247    24753 	   firstname    DOMAIN     2   CREATE DOMAIN firstname AS character varying(30);
 $   DROP DOMAIN freshfitness.firstname;
       freshfitness       postgres    false    9            �           1247    24754    lastname    DOMAIN     1   CREATE DOMAIN lastname AS character varying(30);
 #   DROP DOMAIN freshfitness.lastname;
       freshfitness       postgres    false    9            �           1247    24759    name    DOMAIN     -   CREATE DOMAIN name AS character varying(30);
    DROP DOMAIN freshfitness.name;
       freshfitness       postgres    false    9            �           1247    24766    numberofworkouts    DOMAIN     +   CREATE DOMAIN numberofworkouts AS integer;
 +   DROP DOMAIN freshfitness.numberofworkouts;
       freshfitness       postgres    false    9            �           1247    24757    password    DOMAIN     1   CREATE DOMAIN password AS character varying(30);
 #   DROP DOMAIN freshfitness.password;
       freshfitness       postgres    false    9            �           1247    24758    phonenumber    DOMAIN     &   CREATE DOMAIN phonenumber AS integer;
 &   DROP DOMAIN freshfitness.phonenumber;
       freshfitness       postgres    false    9            �           1247    24763    price    DOMAIN         CREATE DOMAIN price AS integer;
     DROP DOMAIN freshfitness.price;
       freshfitness       postgres    false    9            �           1247    24752    role    DOMAIN     -   CREATE DOMAIN role AS character varying(20);
    DROP DOMAIN freshfitness.role;
       freshfitness       postgres    false    9            �           1247    24760 	   starttime    DOMAIN     3   CREATE DOMAIN starttime AS time without time zone;
 $   DROP DOMAIN freshfitness.starttime;
       freshfitness       postgres    false    9            �           1247    24762    type    DOMAIN     -   CREATE DOMAIN type AS character varying(30);
    DROP DOMAIN freshfitness.type;
       freshfitness       postgres    false    9            �           1247    24764 	   validfrom    DOMAIN     !   CREATE DOMAIN validfrom AS date;
 $   DROP DOMAIN freshfitness.validfrom;
       freshfitness       postgres    false    9            �           1247    24765    validto    DOMAIN        CREATE DOMAIN validto AS date;
 "   DROP DOMAIN freshfitness.validto;
       freshfitness       postgres    false    9            �            1259    24854    activity    TABLE     �   CREATE TABLE activity (
    id integer NOT NULL,
    name pg_catalog.name,
    instructorid integer,
    starttime timestamp without time zone,
    endtime timestamp without time zone
);
 "   DROP TABLE freshfitness.activity;
       freshfitness         postgres    false    9            �            1259    24852    activity_id_seq    SEQUENCE     q   CREATE SEQUENCE activity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE freshfitness.activity_id_seq;
       freshfitness       postgres    false    237    9            8	           0    0    activity_id_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE activity_id_seq OWNED BY activity.id;
            freshfitness       postgres    false    236            �            1259    24807    subscription    TABLE     �   CREATE TABLE subscription (
    id integer NOT NULL,
    userid integer,
    validfrom validfrom,
    validto validto,
    subscriptiontypeid integer
);
 &   DROP TABLE freshfitness.subscription;
       freshfitness         postgres    false    698    9    699            �            1259    24805    subscription_id_seq    SEQUENCE     u   CREATE SEQUENCE subscription_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE freshfitness.subscription_id_seq;
       freshfitness       postgres    false    9    231            9	           0    0    subscription_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE subscription_id_seq OWNED BY subscription.id;
            freshfitness       postgres    false    230            �            1259    24796    subscriptiontype    TABLE     [   CREATE TABLE subscriptiontype (
    id integer NOT NULL,
    type type,
    price price
);
 *   DROP TABLE freshfitness.subscriptiontype;
       freshfitness         postgres    false    696    9    697            �            1259    24794    subscriptiontype_id_seq    SEQUENCE     y   CREATE SEQUENCE subscriptiontype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE freshfitness.subscriptiontype_id_seq;
       freshfitness       postgres    false    9    229            :	           0    0    subscriptiontype_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE subscriptiontype_id_seq OWNED BY subscriptiontype.id;
            freshfitness       postgres    false    228            �            1259    24867    useractivity    TABLE     c   CREATE TABLE useractivity (
    id integer NOT NULL,
    userid integer,
    activityid integer
);
 &   DROP TABLE freshfitness.useractivity;
       freshfitness         postgres    false    9            �            1259    24865    useractivity_id_seq    SEQUENCE     u   CREATE SEQUENCE useractivity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE freshfitness.useractivity_id_seq;
       freshfitness       postgres    false    9    239            ;	           0    0    useractivity_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE useractivity_id_seq OWNED BY useractivity.id;
            freshfitness       postgres    false    238            �            1259    24769    userrole    TABLE     B   CREATE TABLE userrole (
    id integer NOT NULL,
    role role
);
 "   DROP TABLE freshfitness.userrole;
       freshfitness         postgres    false    9    686            �            1259    24767    userrole_id_seq    SEQUENCE     q   CREATE SEQUENCE userrole_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE freshfitness.userrole_id_seq;
       freshfitness       postgres    false    225    9            <	           0    0    userrole_id_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE userrole_id_seq OWNED BY userrole.id;
            freshfitness       postgres    false    224            �            1259    24780    users    TABLE     �   CREATE TABLE users (
    id integer NOT NULL,
    userroleid integer,
    firstname firstname,
    lastname lastname,
    dateofbirth dateofbirth,
    email email,
    password password,
    phonenumber phonenumber
);
    DROP TABLE freshfitness.users;
       freshfitness         postgres    false    690    9    692    691    689    688    687            �            1259    24778    users_id_seq    SEQUENCE     n   CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE freshfitness.users_id_seq;
       freshfitness       postgres    false    9    227            =	           0    0    users_id_seq    SEQUENCE OWNED BY     /   ALTER SEQUENCE users_id_seq OWNED BY users.id;
            freshfitness       postgres    false    226            �            1259    24836    workout    TABLE     �   CREATE TABLE workout (
    id integer NOT NULL,
    userid integer,
    workouttypeid integer,
    numberofworkouts numberofworkouts,
    date timestamp without time zone
);
 !   DROP TABLE freshfitness.workout;
       freshfitness         postgres    false    700    9            �            1259    24834    workout_id_seq    SEQUENCE     p   CREATE SEQUENCE workout_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE freshfitness.workout_id_seq;
       freshfitness       postgres    false    9    235            >	           0    0    workout_id_seq    SEQUENCE OWNED BY     3   ALTER SEQUENCE workout_id_seq OWNED BY workout.id;
            freshfitness       postgres    false    234            �            1259    24825    workouttype    TABLE     E   CREATE TABLE workouttype (
    id integer NOT NULL,
    type type
);
 %   DROP TABLE freshfitness.workouttype;
       freshfitness         postgres    false    696    9            �            1259    24823    workouttype_id_seq    SEQUENCE     t   CREATE SEQUENCE workouttype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE freshfitness.workouttype_id_seq;
       freshfitness       postgres    false    233    9            ?	           0    0    workouttype_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE workouttype_id_seq OWNED BY workouttype.id;
            freshfitness       postgres    false    232            �           2604    24857    activity id    DEFAULT     \   ALTER TABLE ONLY activity ALTER COLUMN id SET DEFAULT nextval('activity_id_seq'::regclass);
 @   ALTER TABLE freshfitness.activity ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    236    237    237            �           2604    24810    subscription id    DEFAULT     d   ALTER TABLE ONLY subscription ALTER COLUMN id SET DEFAULT nextval('subscription_id_seq'::regclass);
 D   ALTER TABLE freshfitness.subscription ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    230    231    231            �           2604    24799    subscriptiontype id    DEFAULT     l   ALTER TABLE ONLY subscriptiontype ALTER COLUMN id SET DEFAULT nextval('subscriptiontype_id_seq'::regclass);
 H   ALTER TABLE freshfitness.subscriptiontype ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    229    228    229            �           2604    24870    useractivity id    DEFAULT     d   ALTER TABLE ONLY useractivity ALTER COLUMN id SET DEFAULT nextval('useractivity_id_seq'::regclass);
 D   ALTER TABLE freshfitness.useractivity ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    238    239    239            �           2604    24772    userrole id    DEFAULT     \   ALTER TABLE ONLY userrole ALTER COLUMN id SET DEFAULT nextval('userrole_id_seq'::regclass);
 @   ALTER TABLE freshfitness.userrole ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    225    224    225            �           2604    24783    users id    DEFAULT     V   ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);
 =   ALTER TABLE freshfitness.users ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    226    227    227            �           2604    24839 
   workout id    DEFAULT     Z   ALTER TABLE ONLY workout ALTER COLUMN id SET DEFAULT nextval('workout_id_seq'::regclass);
 ?   ALTER TABLE freshfitness.workout ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    235    234    235            �           2604    24828    workouttype id    DEFAULT     b   ALTER TABLE ONLY workouttype ALTER COLUMN id SET DEFAULT nextval('workouttype_id_seq'::regclass);
 C   ALTER TABLE freshfitness.workouttype ALTER COLUMN id DROP DEFAULT;
       freshfitness       postgres    false    232    233    233            1	          0    24854    activity 
   TABLE DATA               G   COPY activity (id, name, instructorid, starttime, endtime) FROM stdin;
    freshfitness       postgres    false    237   �V       +	          0    24807    subscription 
   TABLE DATA               S   COPY subscription (id, userid, validfrom, validto, subscriptiontypeid) FROM stdin;
    freshfitness       postgres    false    231   W       )	          0    24796    subscriptiontype 
   TABLE DATA               4   COPY subscriptiontype (id, type, price) FROM stdin;
    freshfitness       postgres    false    229   �W       3	          0    24867    useractivity 
   TABLE DATA               7   COPY useractivity (id, userid, activityid) FROM stdin;
    freshfitness       postgres    false    239   �W       %	          0    24769    userrole 
   TABLE DATA               %   COPY userrole (id, role) FROM stdin;
    freshfitness       postgres    false    225   *X       '	          0    24780    users 
   TABLE DATA               h   COPY users (id, userroleid, firstname, lastname, dateofbirth, email, password, phonenumber) FROM stdin;
    freshfitness       postgres    false    227   eX       /	          0    24836    workout 
   TABLE DATA               M   COPY workout (id, userid, workouttypeid, numberofworkouts, date) FROM stdin;
    freshfitness       postgres    false    235   �[       -	          0    24825    workouttype 
   TABLE DATA               (   COPY workouttype (id, type) FROM stdin;
    freshfitness       postgres    false    233   �\       @	           0    0    activity_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('activity_id_seq', 8, true);
            freshfitness       postgres    false    236            A	           0    0    subscription_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('subscription_id_seq', 20, true);
            freshfitness       postgres    false    230            B	           0    0    subscriptiontype_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('subscriptiontype_id_seq', 2, true);
            freshfitness       postgres    false    228            C	           0    0    useractivity_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('useractivity_id_seq', 20, true);
            freshfitness       postgres    false    238            D	           0    0    userrole_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('userrole_id_seq', 1, false);
            freshfitness       postgres    false    224            E	           0    0    users_id_seq    SEQUENCE SET     3   SELECT pg_catalog.setval('users_id_seq', 5, true);
            freshfitness       postgres    false    226            F	           0    0    workout_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('workout_id_seq', 27, true);
            freshfitness       postgres    false    234            G	           0    0    workouttype_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('workouttype_id_seq', 1, false);
            freshfitness       postgres    false    232            �           2606    24859    activity activity_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY activity
    ADD CONSTRAINT activity_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY freshfitness.activity DROP CONSTRAINT activity_pkey;
       freshfitness         postgres    false    237            �           2606    24812    subscription subscription_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY freshfitness.subscription DROP CONSTRAINT subscription_pkey;
       freshfitness         postgres    false    231            �           2606    24804 &   subscriptiontype subscriptiontype_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY subscriptiontype
    ADD CONSTRAINT subscriptiontype_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY freshfitness.subscriptiontype DROP CONSTRAINT subscriptiontype_pkey;
       freshfitness         postgres    false    229            �           2606    24872    useractivity useractivity_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY useractivity
    ADD CONSTRAINT useractivity_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY freshfitness.useractivity DROP CONSTRAINT useractivity_pkey;
       freshfitness         postgres    false    239            �           2606    24777    userrole userrole_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY userrole
    ADD CONSTRAINT userrole_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY freshfitness.userrole DROP CONSTRAINT userrole_pkey;
       freshfitness         postgres    false    225            �           2606    24788    users users_pkey 
   CONSTRAINT     G   ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY freshfitness.users DROP CONSTRAINT users_pkey;
       freshfitness         postgres    false    227            �           2606    24841    workout workout_pkey 
   CONSTRAINT     K   ALTER TABLE ONLY workout
    ADD CONSTRAINT workout_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY freshfitness.workout DROP CONSTRAINT workout_pkey;
       freshfitness         postgres    false    235            �           2606    24833    workouttype workouttype_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY workouttype
    ADD CONSTRAINT workouttype_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY freshfitness.workouttype DROP CONSTRAINT workouttype_pkey;
       freshfitness         postgres    false    233            �           2606    24860 #   activity activity_instructorid_fkey    FK CONSTRAINT     y   ALTER TABLE ONLY activity
    ADD CONSTRAINT activity_instructorid_fkey FOREIGN KEY (instructorid) REFERENCES users(id);
 S   ALTER TABLE ONLY freshfitness.activity DROP CONSTRAINT activity_instructorid_fkey;
       freshfitness       postgres    false    227    237    2187            �           2606    24818 1   subscription subscription_subscriptiontypeid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_subscriptiontypeid_fkey FOREIGN KEY (subscriptiontypeid) REFERENCES subscriptiontype(id);
 a   ALTER TABLE ONLY freshfitness.subscription DROP CONSTRAINT subscription_subscriptiontypeid_fkey;
       freshfitness       postgres    false    229    2189    231            �           2606    24813 %   subscription subscription_userid_fkey    FK CONSTRAINT     u   ALTER TABLE ONLY subscription
    ADD CONSTRAINT subscription_userid_fkey FOREIGN KEY (userid) REFERENCES users(id);
 U   ALTER TABLE ONLY freshfitness.subscription DROP CONSTRAINT subscription_userid_fkey;
       freshfitness       postgres    false    227    231    2187            �           2606    24878 )   useractivity useractivity_activityid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY useractivity
    ADD CONSTRAINT useractivity_activityid_fkey FOREIGN KEY (activityid) REFERENCES activity(id);
 Y   ALTER TABLE ONLY freshfitness.useractivity DROP CONSTRAINT useractivity_activityid_fkey;
       freshfitness       postgres    false    2197    237    239            �           2606    24873 %   useractivity useractivity_userid_fkey    FK CONSTRAINT     u   ALTER TABLE ONLY useractivity
    ADD CONSTRAINT useractivity_userid_fkey FOREIGN KEY (userid) REFERENCES users(id);
 U   ALTER TABLE ONLY freshfitness.useractivity DROP CONSTRAINT useractivity_userid_fkey;
       freshfitness       postgres    false    2187    227    239            �           2606    24789    users users_userroleid_fkey    FK CONSTRAINT     r   ALTER TABLE ONLY users
    ADD CONSTRAINT users_userroleid_fkey FOREIGN KEY (userroleid) REFERENCES userrole(id);
 K   ALTER TABLE ONLY freshfitness.users DROP CONSTRAINT users_userroleid_fkey;
       freshfitness       postgres    false    227    225    2185            �           2606    24842    workout workout_userid_fkey    FK CONSTRAINT     k   ALTER TABLE ONLY workout
    ADD CONSTRAINT workout_userid_fkey FOREIGN KEY (userid) REFERENCES users(id);
 K   ALTER TABLE ONLY freshfitness.workout DROP CONSTRAINT workout_userid_fkey;
       freshfitness       postgres    false    2187    227    235            �           2606    24847 "   workout workout_workouttypeid_fkey    FK CONSTRAINT        ALTER TABLE ONLY workout
    ADD CONSTRAINT workout_workouttypeid_fkey FOREIGN KEY (workouttypeid) REFERENCES workouttype(id);
 R   ALTER TABLE ONLY freshfitness.workout DROP CONSTRAINT workout_workouttypeid_fkey;
       freshfitness       postgres    false    233    235    2193            1	   y   x���A
�0E���*�@�jԺ�NbP����EЎ����/�Y�9��J2$�	g5��KS{���&�����m^��:;GW�v�����^Aa�C�'�T�Xlؘ���B|�)<�      +	   w   x�e���PD�5��a��l�
^�u�DWL��"�20�@����I��FN᝼	�]�$���K�&a�VG9zg:�Z�$��r6�7�3
=��U�U��/#�����{���7WHJ      )	   '   x�3�JM/�I,�440�2�(J��,��4�b���� �U      3	   >   x�̹ 1A����E��q�׃�aI0[��&��F��r�=j��z4��C�h���H�q,c      %	   +   x�3�tL����2��M�MJ-�2���+.)*M.�/����� ��	�      '	   a  x�]T�v�8]_1?`=,�ޑI`�IzB��F�:�ƶ�m�O�~J�,�ҭ�{o�"���������@�,�H��͠��6W��ڼ9��Nq�`�<O����
�$9BL(!�n�u��µ��_��GD
�w}�vm׺\?ml;�U}��u�2��9�rp�^%����t��Ȍ�*���4�u���=�u�U�,�,�tm������~sz�|�;�|߼
�|�f����	*h��K�{��ꏦ=����x�(�l�ž�T��*ⶸ���݆w@��)M�G�q�NZ7p��|�""2��Ϻ�����K�4�ֽS��mw��|#���,�@%g��a)��(��+���vO���(S�־�PY�N%���_�o�p� �����
:=����<����ε����%Ġ�4z[d�U�E�Q�e�(t����]��0���;���1͋b�7O@�$���נX�ڢ�:8�VGՎ���0��.�A7g�Jm;�&�SQ!���j!���W>/ �)Ks�yd��Nf�>�ʎ�4Bb��j+4�Z5� :v��]K�|����D$��	ԑ��4�K��L��i�3/Tr\��@��V�����\U:�V�?������&IH@)R��F��p��W��wN���� e�N!�}!$a�_Ple�z�����zA��~�	�O�8�{�T���h�-8�g�����n8+ܠ�4=���`���?p�^,��Ȝ�$ͼ���GUU��P�{|I��"#&��&�γ诋ra7�v7�Nv���~�H92C'��Uq�>���گ:��U���l��7�Ô2�_ϕ7Qa��l���䏾�5�Z m���O<�L����o      /	     x�u�ˑ�0�(
'`��>���c��gom�݅��@`f�$��3v�����"�A�a�kp�A3�-pu�\�7�||��{�G�N���|�T%�+�$��R��s�\����/�!HD�#":r��U�(),Ҟђ[��["jr��_��t��e@v/�iM
�m�-p��c�Z ���� �B���50�ƫ`^�	~��t��"��i�,;�	+������疾�MĴ�$��"�0L�H�N�#�����*2�*�	���A]8&��9J)?���      -	   c   x��A�0ѵ�)z�JPZ�.l qC$+	q�r�"��4m��^*��'gU/��ֆ�<�^��M�D�(>�]�;��K���|�0�px��8i̗,O �� �     