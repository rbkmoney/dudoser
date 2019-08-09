ALTER TABLE dudos.payment_payer ADD COLUMN content_type CHARACTER VARYING;
ALTER TABLE dudos.payment_payer ADD COLUMN content_data bytea;

insert into dudos.templates (id, body) values (11, convert_from(decode('PCFkb2N0eXBlIGh0bWw+CjwjYXNzaWduIGNlYk1ldGFkYXRhID0ganNvblBhcnNlKHBheW1lbnRQYXllci5tZXRhZGF0YS5nZXREYXRhVmFsdWUoKSwgImNvbS5yYmttb25leS5kdWRvc2VyLnNlcnZpY2UudGVtcGxhdGUubW9kZWwuQ2ViTWV0YWRhdGEiKT4KPGh0bWwgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGh0bWwiIHhtbG5zOnY9InVybjpzY2hlbWFzLW1pY3Jvc29mdC1jb206dm1sIiB4bWxuczpvPSJ1cm46c2NoZW1hcy1taWNyb3NvZnQtY29tOm9mZmljZTpvZmZpY2UiPgo8aGVhZD4KICAgIDxtZXRhIGNoYXJzZXQ9IlVURi04Ij4KICAgIDxtZXRhIGh0dHAtZXF1aXY9IlgtVUEtQ29tcGF0aWJsZSIgY29udGVudD0iSUU9ZWRnZSI+CiAgICA8bWV0YSBuYW1lPSJ2aWV3cG9ydCIgY29udGVudD0id2lkdGg9ZGV2aWNlLXdpZHRoLCBpbml0aWFsLXNjYWxlPTEiPgogICAgPHRpdGxlPlJCS21vbmV5OiDQo9GB0L/QtdGI0L3Ri9C5INC/0LvQsNGC0LXQtjwjaWYgcGF5bWVudFBheWVyLnNob3BVcmw/Pz4g0L3QsCDRgdCw0LnRgtC1ICR7cGF5bWVudFBheWVyLnNob3BVcmx9PC8jaWY+PC90aXRsZT4KICAgIDxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+CiAgICAgICAgaHRtbCwKICAgICAgICBib2R5IHsKICAgICAgICAgICAgYmFja2dyb3VuZC1jb2xvcjogIzY4NWJmZjsKICAgICAgICAgICAgbWluLWhlaWdodDogMTAwJTsKICAgICAgICAgICAgbWluLXdpZHRoOiA0MDBweDsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgICAgIG1hcmdpbjogMDsKICAgICAgICAgICAgcGFkZGluZzogMDsKICAgICAgICB9CgogICAgICAgICogewogICAgICAgICAgICBmb250LWZhbWlseTogJ1JvYm90bycsICdIZWx2ZXRpY2EgTmV1ZScsICdTZWdvZSBVSScsICdWZXJkYW5hJywgJ1RhaG9tYScsIHNhbnMtc2VyaWY7CiAgICAgICAgfQoKICAgICAgICAub3ZlcmxheV9jb250YWluZXIgewogICAgICAgICAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjNjg1YmZmOwogICAgICAgICAgICBtaW4taGVpZ2h0OiAxMDAlOwogICAgICAgICAgICBtaW4td2lkdGg6IDQwMHB4OwogICAgICAgICAgICB3aWR0aDogMTAwJTsKICAgICAgICAgICAgbWFyZ2luOiAwOwogICAgICAgICAgICBwYWRkaW5nOiAwOwogICAgICAgICAgICBib3JkZXItc3BhY2luZzogMDsKICAgICAgICB9CgogICAgICAgIC5tYWluX2NvbnRhaW5lciB7CiAgICAgICAgICAgIG1heC13aWR0aDogMzQwcHg7CiAgICAgICAgICAgIG1hcmdpbjogYXV0bzsKICAgICAgICAgICAgcGFkZGluZzogMThweCA1cHggMThweCA1cHg7CiAgICAgICAgfQoKICAgICAgICAubWFpbl9sb2dvX2NvbnRhaW5lciB7CiAgICAgICAgICAgIHBhZGRpbmctbGVmdDogMjBweDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X3RhYmxlIHsKICAgICAgICAgICAgYmFja2dyb3VuZC1jb2xvcjogI2ZmZmZmZjsKICAgICAgICAgICAgYm9yZGVyLXJhZGl1czogN3B4OwogICAgICAgICAgICB3aWR0aDogMTAwJTsKICAgICAgICAgICAgYm9yZGVyLXNwYWNpbmc6IDA7CiAgICAgICAgICAgIHBhZGRpbmc6IDE1cHggMjBweDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X21haW5faGVhZGVyIHsKICAgICAgICAgICAgY29sb3I6ICMzOGNkOGY7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMjZweDsKICAgICAgICAgICAgcGFkZGluZy10b3A6IDE4cHg7CiAgICAgICAgICAgIGhlaWdodDogMzhweDsKICAgICAgICAgICAgbWFyZ2luOiBhdXRvOwogICAgICAgICAgICBsaW5lLWhlaWdodDogMzBweDsKICAgICAgICAgICAgdGV4dC1hbGlnbjogY2VudGVyOwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfbWFpbl9kZXNjcmlwdGlvbiB7CiAgICAgICAgICAgIGNvbG9yOiAjMzUzNTM1OwogICAgICAgICAgICBmb250LXNpemU6IDExcHg7CiAgICAgICAgICAgIGxpbmUtaGVpZ2h0OiAxMXB4OwogICAgICAgICAgICBtYXJnaW46IDAgYXV0bzsKICAgICAgICAgICAgdGV4dC1hbGlnbjogY2VudGVyOwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfbWFpbl9kZXNjcmlwdGlvbl9saW5rIHsKICAgICAgICAgICAgcGFkZGluZy10b3A6IDVweDsKICAgICAgICAgICAgaGVpZ2h0OiA0MHB4OwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfZGl2aWRlciB7CiAgICAgICAgICAgIGJvcmRlci10b3A6IDFweCBzb2xpZCAjYzNjM2MzOwogICAgICAgICAgICB3aWR0aDogMTAwJTsKICAgICAgICAgICAgaGVpZ2h0OiAxNXB4OwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfaW5uZXJfdGFibGUgewogICAgICAgICAgICBoZWlnaHQ6IDUwcHg7CiAgICAgICAgICAgIGJvcmRlci1zcGFjaW5nOiAwOwogICAgICAgIH0KCiAgICAgICAgLmNvbnRlbnRfc21hbGxfaGVhZGVyIHsKICAgICAgICAgICAgcGFkZGluZzogMDsKICAgICAgICAgICAgdmVydGljYWwtYWxpZ246IGJvdHRvbTsKICAgICAgICAgICAgY29sb3I6ICNjM2MzYzM7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMTBweDsKICAgICAgICAgICAgZm9udC13ZWlnaHQ6IDcwMDsKICAgICAgICAgICAgdGV4dC10cmFuc2Zvcm06IHVwcGVyY2FzZTsKICAgICAgICAgICAgd2lkdGg6IDcwJTsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X3NtYWxsX2hlYWRlcl9yaWdodCB7CiAgICAgICAgICAgIHdpZHRoOiAzMCU7CiAgICAgICAgfQoKICAgICAgICAuY29udGVudF9zbWFsbF90ZXh0IHsKICAgICAgICAgICAgcGFkZGluZzogMDsKICAgICAgICAgICAgdmVydGljYWwtYWxpZ246IHRvcDsKICAgICAgICAgICAgY29sb3I6ICMzNTM1MzU7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogMTRweDsKICAgICAgICAgICAgZm9udC13ZWlnaHQ6IDcwMDsKICAgICAgICB9CgogICAgICAgIC5jb250ZW50X3NtYWxsX2xhYmVsIHsKICAgICAgICAgICAgYm9yZGVyLXJhZGl1czogM3B4OwogICAgICAgICAgICBib3JkZXI6IDFweCBzb2xpZCAjYzNjM2MzOwogICAgICAgICAgICBjb2xvcjogI2MzYzNjMzsKICAgICAgICAgICAgZm9udC1zaXplOiAxMnB4OwogICAgICAgICAgICB0ZXh0LXRyYW5zZm9ybTogdXBwZXJjYXNlOwogICAgICAgIH0KCiAgICAgICAgLnN1cHBvcnRfdGV4dCB7CiAgICAgICAgICAgIGZvbnQtc2l6ZTogOXB4OwogICAgICAgICAgICBjb2xvcjogI2E3YjlmOTsKICAgICAgICAgICAgcGFkZGluZzogMCA1cHg7CiAgICAgICAgfQoKICAgICAgICAuc3VwcG9ydF9saW5rIHsKICAgICAgICAgICAgY29sb3I6ICNhN2I5Zjk7CiAgICAgICAgICAgIHRleHQtZGVjb3JhdGlvbjogbm9uZTsKICAgICAgICB9CgogICAgICAgIC5zdXBwb3J0X2VtYWlsX2xpbmsgewogICAgICAgICAgICBjb2xvcjogI2E3YjlmOTsKICAgICAgICAgICAgdGV4dC1kZWNvcmF0aW9uOiBub25lOwogICAgICAgICAgICBmb250LXdlaWdodDogYm9sZDsKICAgICAgICB9CgogICAgICAgIC5yaWdodCB7CiAgICAgICAgICAgIHRleHQtYWxpZ246IHJpZ2h0OwogICAgICAgIH0KCiAgICAgICAgLndfMTAwIHsKICAgICAgICAgICAgd2lkdGg6IDEwMCU7CiAgICAgICAgfQoKICAgICAgICAubGluayB7CiAgICAgICAgICAgIGNvbG9yOiAjNjg1YmZmOwogICAgICAgICAgICB0ZXh0LWRlY29yYXRpb246IG5vbmU7CiAgICAgICAgfQoKICAgICAgICAuYW1vdW50IHsKICAgICAgICAgICAgZm9udC1zaXplOiAxOHB4OwogICAgICAgIH0KICAgIDwvc3R5bGU+CjwvaGVhZD4KCjxib2R5PgoKPHRhYmxlIGNsYXNzPSJvdmVybGF5X2NvbnRhaW5lciI+CiAgICA8dHI+CiAgICAgICAgPHRkPgogICAgICAgICAgICA8Y2VudGVyPgogICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJtYWluX2NvbnRhaW5lciI+CiAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9Im1haW5fbG9nb19jb250YWluZXIiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgPGltZyBoZWlnaHQ9IjM0cHgiIHdpZHRoPT0iNjdweCIgc3JjPSJodHRwOi8vcmJrLm1vbmV5L21haWwvaW1nL2xvZ28ucG5nIiBjbGFzcz0ibWFpbl9sb2dvIiBhbHQ9IlJCSy5tb25leSBsb2dvIj4KICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X3RhYmxlIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBhbGlnbj0iY2VudGVyIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxoMyBjbGFzcz0iY29udGVudF9tYWluX2hlYWRlciI+0KPRgdC/0LXRiNC90YvQuSDQv9C70LDRgtC10LY8L2gzPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGg1IGNsYXNzPSJjb250ZW50X21haW5fZGVzY3JpcHRpb24iPtCR0LvQsNCz0L7QtNCw0YDQuNC8INC30LAg0L7Qv9C70LDRgtGDINC30LDQutCw0LfQsCDQvdCwINGB0LDQudGC0LU8L2g1PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBwYXltZW50UGF5ZXIuc2hvcFVybD8/PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPGg1IGNsYXNzPSJjb250ZW50X21haW5fZGVzY3JpcHRpb24gY29udGVudF9tYWluX2Rlc2NyaXB0aW9uX2xpbmsiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxhIGhyZWY9IiR7cGF5bWVudFBheWVyLnNob3BVcmx9IiBjbGFzcz0ibGluayI+JHtwYXltZW50UGF5ZXIuc2hvcFVybH08L2E+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L2g1PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8LyNpZj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2RpdmlkZXIiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QodGD0LzQvNCwPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPjwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0IGFtb3VudCI+JHtmb3JtYXR0ZWRBbW91bnR9PC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlcl9yaWdodCByaWdodCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8c3BhbiBjbGFzcz0iY29udGVudF9zbWFsbF9sYWJlbCI+Jm5ic3A7Jm5ic3A70J7Qv9C70LDRh9C10L3QviZuYnNwOyZuYnNwOzwvc3Bhbj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90YWJsZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCd0L7QvNC10YAg0LjQt9Cy0LXRidC10L3QuNGPPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPiR7cGF5bWVudFBheWVyLmludm9pY2VJZH08L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0JTQsNGC0LA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8IS0tIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXJfcmlnaHQgY29udGVudF9zbWFsbF9oZWFkZXIgcmlnaHQiPlRpbWU8L3RkPiAtLT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPiR7cGF5bWVudFBheWVyLmRhdGUuZm9ybWF0KCdkZC5NTS55eXl5Jyl9PC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCEtLSA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyX3JpZ2h0IGNvbnRlbnRfc21hbGxfdGV4dCByaWdodCI+MjE6Mzg8L3RkPiAtLT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90YWJsZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCd0LDQvNC10L3QvtCy0LDQvdC40LUg0L7Qv9C10YDQsNGG0LjQuDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0Ij4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgINCf0LXRgNC10LLQvtC0INC00LvRjyDQv9C+0L/QvtC70L3QtdC90LjRjyDQsdCw0L3QutC+0LLRgdC60L7Qs9C+INGB0YfQtdGC0LAg0JIg0JDQniAi0JrRgNC10LTQuNGCINCV0LLRgNC+0L/QsCDQkdCw0L3QuiIKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90YWJsZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwjaWYgY2ViTWV0YWRhdGEuY3VzdG9tZXJJbml0aWFscz8/PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCf0LvQsNGC0LXQu9GM0YnQuNC6PC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgJHtjZWJNZXRhZGF0YS5jdXN0b21lckluaXRpYWxzfQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBjZWJNZXRhZGF0YS5hY2NvdW50Pz8+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGFibGUgY2xhc3M9ImNvbnRlbnRfaW5uZXJfdGFibGUgd18xMDAiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX2hlYWRlciI+0J3QvtC80LXRgCDRgdGH0LXRgtCwINC/0L7Qu9GD0YfQsNGC0LXQu9GPPC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPiR7Y2ViTWV0YWRhdGEuYWNjb3VudH08L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBjZWJNZXRhZGF0YS5jb21taXNzaW9uPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QmtC+0LzQuNGB0YHQuNC40Y88L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+JHtjZWJNZXRhZGF0YS5jb21taXNzaW9uQW1vdW50fSAke3BheW1lbnRQYXllci5jdXJyZW5jeX08L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBjZWJNZXRhZGF0YS5zdW0/Pz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj7QodGD0LzQvNCwINC/0LXRgNC10LLQvtC00LA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfdGV4dCI+JHtjZWJNZXRhZGF0YS5zdW0/c3RyaW5nKCIwLjAwIil9ICR7cGF5bWVudFBheWVyLmN1cnJlbmN5fTwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8LyNpZj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0YWJsZSBjbGFzcz0iY29udGVudF9pbm5lcl90YWJsZSB3XzEwMCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9ImNvbnRlbnRfc21hbGxfaGVhZGVyIj5FbWFpbDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF90ZXh0Ij4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxhIGNsYXNzPSJsaW5rIiBocmVmPSJtYWlsdG86JHtwYXltZW50UGF5ZXIudG9SZWNlaXZlcn0iPiR7cGF5bWVudFBheWVyLnRvUmVjZWl2ZXJ9PC9hPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8I2lmIHBheW1lbnRQYXllci5jYXJkVHlwZT8/PgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRhYmxlIGNsYXNzPSJjb250ZW50X2lubmVyX3RhYmxlIHdfMTAwIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXIiPtCh0L/QvtGB0L7QsSDQvtC/0LvQsNGC0Ys8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8dGQ+PC90ZD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RyPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHRkIGNsYXNzPSJjb250ZW50X3NtYWxsX3RleHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBwYXltZW50UGF5ZXIuY2FyZFR5cGU/Pz4g0JrQsNGA0YLQsCAke3BheW1lbnRQYXllci5jYXJkVHlwZX0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxicj4gKioqKiAke3BheW1lbnRQYXllci5jYXJkTWFza1Bhbn0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDx0ZCBjbGFzcz0iY29udGVudF9zbWFsbF9oZWFkZXJfcmlnaHQgcmlnaHQiPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPCNpZiBwYXltZW50UGF5ZXIuY2FyZFR5cGU/Pz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxpbWcgaGVpZ2h0PSIzMnB4IiB3aWR0aD01NnB4IiBzcmM9Imh0dHA6Ly9yYmsubW9uZXkvbWFpbC9pbWcvaWNvbnMvJHtwYXltZW50UGF5ZXIuY2FyZFR5cGV9LnBuZyIgYWx0PSJQYXltZW50IHN5c3RlbSBsb2dvIiBjbGFzcz0icGF5bWVudF9zeXN0ZW0gIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC8jaWY+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGQ+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPC90cj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdGFibGU+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvI2lmPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICAgICAgPHRyPgogICAgICAgICAgICAgICAgICAgICAgICA8dGQgY2xhc3M9InN1cHBvcnRfY29udGFpbmVyICI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8cCBjbGFzcz0ic3VwcG9ydF90ZXh0Ij4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDQndC10LHQsNC90LrQvtCy0YHQutCw0Y8g0LrRgNC10LTQuNGC0L3QsNGPINC+0YDQs9Cw0L3QuNC30LDRhtC40Y8gItCt0LvQtdC60YLRgNC+0L3QvdGL0Lkg0L/Qu9Cw0YLQtdC20L3Ri9C5INGB0LXRgNCy0LjRgSIgKNCe0LHRidC10YHRgtCy0L4g0YEg0L7Qs9GA0LDQvdC40YfQtdC90L3QvtC5INC+0YLQstC10YHRgtCy0LXQvdC90L7RgdGC0YwpCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg0LDQtNGA0LXRgSDQvNC10YHRgtC+0L3QsNGF0L7QttC00LXQvdC40Y86IDExNTA5Mz8g0JzQvtGB0LrQstCwLCDRg9C7LiDQn9Cw0LLQu9C+0LLRgdC60LDRjywg0LQuNywg0YHRgtGALjEsINCY0J3QnSA3NzUwMDA1NzAwLCDQkdCY0JogMDQ0NTI1MzEzLAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgINC+0YHRg9GJ0LXRgdGC0LLQu9GP0LXRgiDRgdCy0L7RjiDQtNC10Y/RgtC10LvRjNC90L7RgdGC0Ywg0L3QsCDQvtGB0L3QvtCy0LDQvdC40Lgg0LvQuNGG0LXQvdC30LjQuCDQkdCw0L3QutCwINCg0L7RgdGB0LjQuCDihJYgMzUwOS3QmiDQvtGCIDExLjAyLjIwMTPQsywKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDRgdCw0LnRggogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxhIGhyZWY9Imh0dHBzOi8vcmJrLm1vbmV5IiBjbGFzcz0ic3VwcG9ydF9saW5rIj5odHRwczovL3Jiay5tb25leTwvYT4uCiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8L3A+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8cCBjbGFzcz0ic3VwcG9ydF90ZXh0Ij4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICDQldGB0LvQuCDQvtC/0LXRgNCw0YbQuNGPINGB0L7QstC10YDRiNC10L3QsCDQvdC1INCy0LDQvNC4LCDQvdCw0L/QuNGI0LjRgtC1INGB0L7QvtGC0LLQtdGC0YHRgtCy0YPRjtGJ0LXQtSDQt9Cw0Y/QstC70LXQvdC40LUg0L3QsCDQvdCw0YjRgyDRjdC70LXQutGC0YDQvtC90L3Rg9GOINC/0L7Rh9GC0YMKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8YSBocmVmPSJtYWlsdG86c3VwcG9ydEByYmsubW9uZXkgIiBjbGFzcz0ic3VwcG9ydF9lbWFpbF9saW5rICI+c3VwcG9ydEByYmsubW9uZXk8L2E+LgogICAgICAgICAgICAgICAgICAgICAgICAgICAgPC9wPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgPHAgY2xhc3M9InN1cHBvcnRfdGV4dCI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg0KLQtdC70LXRhNC+0L0g0LTQu9GPINGB0L/RgNCw0LLQvtC6INC/0L4g0LLQvtC/0YDQvtGB0LDQvCDQv9GA0L7QstC10LTQtdC90LjRjyDQv9C70LDRgtC10LbQtdC5IDggODAwIDIwMC04OC0yMAogICAgICAgICAgICAgICAgICAgICAgICAgICAgPC9wPgogICAgICAgICAgICAgICAgICAgICAgICA8L3RkPgogICAgICAgICAgICAgICAgICAgIDwvdHI+CiAgICAgICAgICAgICAgICA8L3RhYmxlPgogICAgICAgICAgICA8L2NlbnRlcj4KICAgICAgICA8L3RkPgogICAgPC90cj4KPC90YWJsZT4KPC9ib2R5Pgo8L2h0bWw+Cg==', 'base64'), 'UTF8'));
